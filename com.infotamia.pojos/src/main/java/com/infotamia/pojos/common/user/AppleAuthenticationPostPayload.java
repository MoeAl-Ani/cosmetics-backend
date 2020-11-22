package com.infotamia.pojos.common.user;

/**
 * @author Mohammed Al-Ani
 */
public class AppleAuthenticationPostPayload {
    private String idToken;
    private String authorizationCode;

    public AppleAuthenticationPostPayload() {
        //
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}
