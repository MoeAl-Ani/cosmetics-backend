package com.infotamia.access;

import java.io.Serializable;
import java.security.Principal;

/**
 * Base principal generic type.
 *
 * @author Mohammed Al-Ani
 */
public interface AbstractPrincipal extends Principal, Serializable {
    Integer getId();

    String getUUID();

    Integer getLanguageId();

    SessionType getSessionType();

    AccessService getAccessService();

    AuthenticationProvider getAuthenticationProvider();

    String getAuthenticationProviderAccessToken();
}
