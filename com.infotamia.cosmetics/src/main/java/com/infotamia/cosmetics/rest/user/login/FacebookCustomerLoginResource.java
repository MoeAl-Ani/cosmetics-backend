package com.infotamia.cosmetics.rest.user.login;

import com.infotamia.access.AuthenticationProvider;
import com.infotamia.access.SessionType;
import com.infotamia.base.rest.AbstractResourceHandler;
import com.infotamia.cosmetics.services.oauth.AuthService;
import com.infotamia.cosmetics.services.oauth.AuthState;
import com.infotamia.cosmetics.services.oauth.BaseOAuth20Service;
import com.infotamia.cosmetics.services.user.UserService;
import com.infotamia.cosmetics.transaction.CosmeticsTransactional;
import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.DataCorruptedException;
import com.infotamia.exception.OperationNotAllowedException;
import com.infotamia.exception.RestCoreException;
import com.infotamia.jwt.services.JwtTokenService;
import com.infotamia.logger.LoggerFactory;
import com.infotamia.pojos.common.AppState;
import com.infotamia.pojos.common.user.AuthenticationToken;
import com.infotamia.pojos.common.user.ExternalAccount;
import com.infotamia.pojos.entities.UserEntity;
import org.apache.logging.log4j.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Mohammed Al-Ani
 */
@Path("/auth/facebook")
@RequestScoped
public class FacebookCustomerLoginResource implements AbstractResourceHandler {

    private static final Logger logger = LoggerFactory.getLogger(FacebookCustomerLoginResource.class);
    @Inject
    private UserService userService;
    @Inject
    private JwtTokenService jwtTokenService;
    @Inject
    @AuthService(value = AuthenticationProvider.FACEBOOK)
    private BaseOAuth20Service facebookAuthenticationService;
    @Inject
    @AuthState
    private AppState state;

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @CosmeticsTransactional
    public Response getRedirect() throws URISyntaxException {
        String authorizationUrl = facebookAuthenticationService.getAuthorizationUrl();
        return Response.temporaryRedirect(new URI(authorizationUrl))
                .header("X-XSRF-TOKEN", this.state.getDigitalSignature())
                .build();
    }

    @GET
    @Path("callback")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    @CosmeticsTransactional
    public Response callback(
            @QueryParam("code") String code,
            @QueryParam("state") String state
    ) {
        jwtTokenService.verifyAndReturnAuthTokenDetails(state);
        String accessToken = facebookAuthenticationService.getAccessToken(code);
        try {
            ExternalAccount externalAccount = (ExternalAccount) facebookAuthenticationService.getAccountDetails(accessToken);
            userService.findUserByEmail(externalAccount.getEmail())
                    .or(() -> {
                        UserEntity userEntity = new UserEntity();
                        userEntity.setFirstName(externalAccount.getFirst_name());
                        userEntity.setLastName(externalAccount.getLast_name());
                        userEntity.setEmail(externalAccount.getEmail());
                        userEntity.setCreatedAt(LocalDateTime.now());
                        userEntity.setLanguageId(1);
                        try {
                            return Optional.of(userService.insertUser(userEntity));
                        } catch (DataCorruptedException e) {
                            logger.error(e);
                            return Optional.empty();
                        }
                    }).orElseThrow(() -> new DataCorruptedException("user not created", BaseErrorCode.UNKNOWN));
            String jwt = jwtTokenService.issueJwtToken(
                    externalAccount.getEmail(), AuthenticationProvider.FACEBOOK, accessToken, SessionType.CUSTOMER);
            String data = "";
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("html/facebook_success.html"))))) {
                data = reader.lines().collect(Collectors.joining());
            }
            if (data.trim().length() > 0) {
                data = data.replaceAll("\\{\\{JWT\\}\\}", jwt);
            }
            return Response.ok()
                    .header("Authorization", "Bearer " + jwt)
                    .entity(data.getBytes())
                    .build();
        } catch (DataCorruptedException e) {
            throw new RestCoreException(500, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new RestCoreException(500, BaseErrorCode.UNKNOWN, e.getMessage());
        }
    }

    @POST
    @Path("login")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_JSON)
    @CosmeticsTransactional
    public Response signUp(AuthenticationToken loginToken) {
        try {
            if (loginToken == null || loginToken.getToken() == null || loginToken.getToken().isBlank()) {
                //byte[] bytes = fileService.loadFile("html/facebook_fail.html");
                return Response.status(Response.Status.UNAUTHORIZED)
                        //.entity(bytes)
                        .build();
            }
            ExternalAccount externalAccount = (ExternalAccount) facebookAuthenticationService.getAccountDetails(loginToken.getToken());
            userService.findUserByEmail(externalAccount.getEmail())
                    .orElseThrow(() -> new OperationNotAllowedException("email not found", BaseErrorCode.UNAUTHORIZED));
            String jwt = jwtTokenService.issueJwtToken(
                    externalAccount.getEmail(), AuthenticationProvider.FACEBOOK, loginToken.getToken(), SessionType.CUSTOMER);
            //byte[] facebookSuccessBytes = fileService.loadFile("html/facebook_success.html");
            Response.ResponseBuilder responseBuilder = Response.ok()
                    //.entity(facebookSuccessBytes)
                    .header("jwt", jwt);
            return responseBuilder.header("Authorization", "Bearer " + jwt).build();
        } catch (OperationNotAllowedException e) {
            throw new RestCoreException(403, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            throw new RestCoreException(500, BaseErrorCode.UNKNOWN, e.getMessage());
        }
    }


}
