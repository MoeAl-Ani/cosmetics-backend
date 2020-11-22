package com.infotamia.config;

/**
 * Application login jwt config.
 *
 * @author Mohammed Al-Ani
 */
public class JwtConfiguration {

    private String issuer;
    private String secret;
    private Long expireAt;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Long expireAt) {
        this.expireAt = expireAt;
    }
}
