package com.infotamia.config;

/**
 * General Authentication config.
 *
 * @author Mohammed Al-Ani
 */
public class AuthenticationConfig {
    private FacebookAuthenticationConfiguration facebook;
    private GoogleAuthenticationConfiguration google;

    public AuthenticationConfig() {
    }

    public FacebookAuthenticationConfiguration getFacebook() {
        return facebook;
    }

    public void setFacebook(FacebookAuthenticationConfiguration facebook) {
        this.facebook = facebook;
    }

    public GoogleAuthenticationConfiguration getGoogle() {
        return google;
    }

    public void setGoogle(GoogleAuthenticationConfiguration google) {
        this.google = google;
    }
}
