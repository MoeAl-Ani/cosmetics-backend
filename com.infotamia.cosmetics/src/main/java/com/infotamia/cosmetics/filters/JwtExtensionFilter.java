package com.infotamia.cosmetics.filters;

import com.infotamia.access.AuthenticationProvider;
import com.infotamia.access.SessionType;
import com.infotamia.cosmetics.factory.AbstractCDIFactory;
import com.infotamia.jwt.services.JwtTokenDetails;
import com.infotamia.jwt.services.JwtTokenService;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import org.apache.commons.lang3.StringUtils;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * The idea behind this filter to check if the http request authorization header jwt is close to expire then issue new
 * JWT and update the response authorization header.
 *
 * @author Mohammed Al-Ani
 */
@Provider
@Priority(value = 400)
public class JwtExtensionFilter implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) {

        JwtTokenService jwtTokenService = AbstractCDIFactory.select(JwtTokenService.class);
        String authorizationHeader = containerRequestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        String jwt = null;
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        }
        if (StringUtils.isEmpty(jwt)) {
            return;
        }

        JwtTokenDetails jwtTokenDetails;
        try {
            jwtTokenDetails = jwtTokenService.verifyAndReturnAuthTokenDetails(jwt);
            LocalDateTime exp = jwtTokenDetails.getExp().toLocalDateTime();
            long hours = Duration.between(LocalDateTime.now(), exp).toHours();
            if (hours < 48) {
                String email = jwtTokenDetails.getSub();
                AuthenticationProvider provider = jwtTokenDetails.getProvider();
                String accessToken = jwtTokenDetails.getAccessToken();
                SessionType sessionType = jwtTokenDetails.getSessionType();
                String newJwt = jwtTokenService.issueJwtToken(email, provider, accessToken, sessionType);
                containerResponseContext.getHeaders().add(HttpHeaders.AUTHORIZATION, "Bearer " + newJwt);
            }
        } catch (Exception ignore) {

        }
    }
}
