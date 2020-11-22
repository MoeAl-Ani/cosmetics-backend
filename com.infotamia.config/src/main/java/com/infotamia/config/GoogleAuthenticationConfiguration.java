package com.infotamia.config;

/**
 * Google login configuration bean.
 *
 * @author Mohammed Al-Ani
 */
public class GoogleAuthenticationConfiguration {

    private String clientId;
    private String clientSecret;
    private String scope;
    private String callbackUrl;
    private String callbackAdminUrl;
    private String profileUrl;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getCallbackAdminUrl() {
        return callbackAdminUrl;
    }

    public void setCallbackAdminUrl(String callbackAdminUrl) {
        this.callbackAdminUrl = callbackAdminUrl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }
}
