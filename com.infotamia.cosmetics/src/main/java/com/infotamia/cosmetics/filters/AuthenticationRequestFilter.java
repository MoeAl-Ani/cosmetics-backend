package com.infotamia.cosmetics.filters;

import com.infotamia.access.AuthenticationProvider;
import com.infotamia.access.BaseSecurityContext;
import com.infotamia.access.SessionType;
import com.infotamia.cosmetics.access.CosmeticUserPrincipal;
import com.infotamia.cosmetics.access.CustomerAccessService;
import com.infotamia.cosmetics.access.GuestAccessService;
import com.infotamia.cosmetics.access.SystemAdminAccessService;
import com.infotamia.cosmetics.factory.AbstractCDIFactory;
import com.infotamia.cosmetics.services.user.UserService;
import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.RestCoreException;
import com.infotamia.jwt.services.JwtTokenDetails;
import com.infotamia.jwt.services.JwtTokenService;
import com.infotamia.logger.LoggerFactory;
import com.infotamia.pojos.entities.UserEntity;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import java.util.Optional;
import java.util.Random;


/**
 * PreMatching filter. All API requests with no exceptions come throw this filter for processing.
 * User principal construction will be done if the http request has the valid headers.
 * if jwt is exist and not valid then request will terminated with 401, if jwt not exist then guest
 * principal will be created.
 *
 * @author Mohammed Al-Ani
 * @throws RestCoreException
 */
@Provider
@PreMatching
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationRequestFilter implements ContainerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationRequestFilter.class);

    @Override
    public void filter(ContainerRequestContext requestContext) {
        logger.info(requestContext.getUriInfo().getRequestUri().getPath());

        // skip filter is useful to skip requests from further processing.
        if (SkipFilterUtils.shouldSkip(requestContext.getUriInfo().getRequestUri().getPath())) {
            return;
        }
        JwtTokenService jwtTokenService = AbstractCDIFactory.select(JwtTokenService.class);
        UserService userService = AbstractCDIFactory.select(UserService.class);
        String jwt = "";
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        }
        if (StringUtils.isNoneBlank(jwt)) {
            JwtTokenDetails jwtTokenDetails;
            try {
                jwtTokenDetails = jwtTokenService.verifyAndReturnAuthTokenDetails(jwt);
                String email = jwtTokenDetails.getSub();
                AuthenticationProvider provider = jwtTokenDetails.getProvider();
                String accessToken = jwtTokenDetails.getAccessToken();
                Optional<UserEntity> optionalUsersEntity = userService.findUserByEmail(email);
                if (optionalUsersEntity.isPresent()) {
                    logger.info("user found");
                    UserEntity user = optionalUsersEntity.get();
                    if (!SessionType.GUEST.equals(jwtTokenDetails.getSessionType())) {
                        CosmeticUserPrincipal principal = new CosmeticUserPrincipal();
                        principal.setId(user.getId());
                        principal.setName(email);
                        principal.setLanguageId(user.getLanguageId());
                        principal.setAuthenticationProvider(provider);
                        principal.setAuthenticationProviderAccessToken(accessToken);
                        if (SessionType.SYSTEM_ADMIN.equals(jwtTokenDetails.getSessionType())) {
                            principal.setAccessService(new SystemAdminAccessService());
                            principal.setSessionType(SessionType.SYSTEM_ADMIN);
                        } else {
                            principal.setAccessService(new CustomerAccessService());
                            principal.setSessionType(SessionType.CUSTOMER);
                        }
                        BaseSecurityContext sc = new BaseSecurityContext(principal);
                        requestContext.setSecurityContext(sc);
                    } else requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build());
                } else {
                    requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED.getStatusCode()).build());
                }
            } catch (Exception e) {
                logger.error("something went wrong", e);
                throw new RestCoreException(401, BaseErrorCode.UNAUTHORIZED, e.getMessage());
            }
        } else {
            CosmeticUserPrincipal principal = new CosmeticUserPrincipal();
            principal.setId(new Random().nextInt(100000));
            principal.setName("GUEST");
            principal.setLanguageId(1);
            principal.setAuthenticationProvider(AuthenticationProvider.GUEST);
            principal.setSessionType(SessionType.GUEST);
            principal.setAccessService(new GuestAccessService());
            BaseSecurityContext sc = new BaseSecurityContext(principal);
            requestContext.setSecurityContext(sc);
        }
    }
}
