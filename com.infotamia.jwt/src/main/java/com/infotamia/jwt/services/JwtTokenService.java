package com.infotamia.jwt.services;

import com.infotamia.access.AuthenticationProvider;
import com.infotamia.access.SessionType;
import com.infotamia.config.ConfigEnum;
import com.infotamia.config.ConfigServiceQualifier;
import com.infotamia.config.JwtConfiguration;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author Mohammed Al-Ani
 */
@ApplicationScoped
public class JwtTokenService {
    @Inject
    @ConfigServiceQualifier(ConfigEnum.JWT)
    private JwtConfiguration jwtConfiguration;
    @Inject
    private JwtTokenIssuer tokenIssuer;
    @Inject
    private JwtTokenVerifier jwtTokenVerifier;

    public String issueJwtToken(String subject) {
        return issueJwtToken(subject, null, null, null);
    }

    public String issueJwtToken(String subject, AuthenticationProvider provider, String accessToken, SessionType sessionType) {
        String id = generateTokenIdentifier();
        ZonedDateTime issuedDate = ZonedDateTime.now();
        ZonedDateTime expirationDate = calculateExpirationDate(issuedDate);

        JwtTokenDetails jwtTokenDetails = new JwtTokenDetails(
                id,
                subject,
                sessionType,
                provider,
                accessToken,
                issuedDate,
                expirationDate
        );

        return tokenIssuer.issueJwtToken(jwtTokenDetails);
    }


    public JwtTokenDetails verifyAndReturnAuthTokenDetails(String token) {
        return jwtTokenVerifier.verifyAndGetJwtToken(token);
    }

    private ZonedDateTime calculateExpirationDate(ZonedDateTime issuedDate) {
        return issuedDate.plusSeconds(jwtConfiguration.getExpireAt());
    }

    private String generateTokenIdentifier() {
        return UUID.randomUUID().toString();
    }
}