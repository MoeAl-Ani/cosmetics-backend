package com.infotamia.jwt.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.infotamia.config.ConfigEnum;
import com.infotamia.config.ConfigServiceQualifier;
import com.infotamia.config.JwtConfiguration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * CDI jwt verifier service.
 *
 * @author Mohammed Al-Ani
 */
@ApplicationScoped
class JwtTokenVerifier {
    @Inject
    @ConfigServiceQualifier(ConfigEnum.JWT)
    private JwtConfiguration jwtConfiguration;

    JwtTokenDetails verifyAndGetJwtToken(String jwtToken) {

        Algorithm algorithm = Algorithm.HMAC256(jwtConfiguration.getSecret());
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(jwtConfiguration.getIssuer())
                .build();
        DecodedJWT jwt = verifier.verify(jwtToken);
        return new JwtTokenDetails(
                JwtDecoderUtils.getJwtTokenId(jwt),
                JwtDecoderUtils.getJwtSubject(jwt),
                JwtDecoderUtils.getJwtSessionType(jwt),
                JwtDecoderUtils.getJwtAuthenticationProvider(jwt),
                JwtDecoderUtils.getJwtAuthenticationProviderAccessToken(jwt),
                JwtDecoderUtils.getJwtIssueAt(jwt),
                JwtDecoderUtils.getJwtExpireAt(jwt));
    }
}