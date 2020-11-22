package com.infotamia.cosmetics.access;

import com.infotamia.access.AbstractPrincipal;
import com.infotamia.access.AccessService;
import com.infotamia.access.AuthenticationProvider;
import com.infotamia.access.SessionType;

import java.io.Serializable;

/**
 * Cosmetic user principal.
 *
 * @author Mohammed Al-Ani
 */
public class CosmeticUserPrincipal implements AbstractPrincipal, Serializable {
    private Integer id;
    private String UUID;
    private Integer languageId;
    private String name;
    private AccessService accessService;
    private AuthenticationProvider authenticationProvider;
    private String authenticationProviderAccessToken;
    private SessionType sessionType;

    public CosmeticUserPrincipal() {
        // no args constructor.
    }

    /**
     * @return the name of the principal, name === email.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * set user email. name === email
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the unique database user identifier.
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * set user unique identifier.
     *
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return uuid of the current session.
     */
    @Override
    public String getUUID() {
        return UUID;
    }

    /**
     * set the session uuid
     *
     * @param UUID
     */
    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    /**
     * @return user language preference.
     */
    @Override
    public Integer getLanguageId() {
        return languageId;
    }

    /**
     * set user language preference.
     *
     * @param languageId
     */
    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    /**
     * @return the current session type.
     */
    @Override
    public SessionType getSessionType() {
        return sessionType;
    }

    public void setSessionType(SessionType sessionType) {
        this.sessionType = sessionType;
    }

    /**
     * @return principal access service.
     */
    @Override
    public AccessService getAccessService() {
        return accessService;
    }

    /**
     * set user principal cosmetics access service.
     *
     * @param accessService
     */
    public void setAccessService(AccessService accessService) {
        this.accessService = accessService;
    }

    /**
     * @return Login authentication provider.
     */
    @Override
    public AuthenticationProvider getAuthenticationProvider() {
        return authenticationProvider;
    }

    /**
     * set login authentication provider.
     *
     * @param authenticationProvider
     */
    public void setAuthenticationProvider(AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
    }

    /**
     * @return Login access token.
     */
    @Override
    public String getAuthenticationProviderAccessToken() {
        return authenticationProviderAccessToken;
    }

    /**
     * set login access token to the current principal.
     *
     * @param authenticationProviderAccessToken
     */
    public void setAuthenticationProviderAccessToken(String authenticationProviderAccessToken) {
        this.authenticationProviderAccessToken = authenticationProviderAccessToken;
    }
}
