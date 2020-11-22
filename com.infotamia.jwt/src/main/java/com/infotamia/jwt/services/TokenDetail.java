package com.infotamia.jwt.services;

import com.infotamia.access.AuthenticationProvider;
import com.infotamia.access.SessionType;

import java.time.ZonedDateTime;

/**
 * base token details bean.
 *
 * @author Mohammed Al-Ani
 */
public interface TokenDetail {
    String getJwtId();

    String getKeyId();

    String getIssuer();

    String getAud();

    String getSub();

    String getEmail();

    SessionType getSessionType();

    AuthenticationProvider getProvider();

    String getAccessToken();

    ZonedDateTime getIat();

    ZonedDateTime getExp();

}
