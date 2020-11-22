package com.infotamia.jwt.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.infotamia.config.ConfigEnum;
import com.infotamia.config.ConfigServiceQualifier;
import com.infotamia.config.JwtConfiguration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.util.Date;

/**
 * CDI jwt issuer service.
 *
 * @author Mohammed Al-Ani
 */
@ApplicationScoped
class JwtTokenIssuer {
    @Inject
    @ConfigServiceQualifier(ConfigEnum.JWT)
    private JwtConfiguration jwtConfiguration;

    public String issueJwtToken(JwtTokenDetails jwtTokenDetails) {
        Algorithm algorithm = Algorithm.HMAC256(jwtConfiguration.getSecret());
        String sessionType = jwtTokenDetails.getSessionType() != null ? jwtTokenDetails.getSessionType().name() : null;
        String authenticationProvider = jwtTokenDetails.getProvider() != null ? jwtTokenDetails.getProvider().name() : null;
        return JWT.create()
                .withIssuer(jwtConfiguration.getIssuer())
                .withJWTId(jwtTokenDetails.getJwtId())
                .withSubject(jwtTokenDetails.getSub())
                .withClaim("sessionType", sessionType)
                .withClaim("authenticationProvider", authenticationProvider)
                .withClaim("accessToken", jwtTokenDetails.getAccessToken())
                .withIssuedAt(Date.from(jwtTokenDetails.getIat().toInstant()))
                .withExpiresAt(Date.from(jwtTokenDetails.getExp().toInstant()))
                .sign(algorithm);
    }
}