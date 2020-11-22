package com.infotamia.config;

import java.util.List;

/**
 * application configuration bean.
 *
 * @author Mohammed Al-Ani
 */
public class ConfigBean {
    private AuthenticationConfig authenticationConfig;
    private JwtConfiguration jwt;
    private AwsS3Configuration awsS3;
    private List<String> redisReplicas;
    private PaymentConfiguration payment;
    private EmailConfiguration email;

    public ConfigBean() {
        //
    }

    public AuthenticationConfig getAuthenticationConfig() {
        return authenticationConfig;
    }

    public void setAuthenticationConfig(AuthenticationConfig authenticationConfig) {
        this.authenticationConfig = authenticationConfig;
    }

    public JwtConfiguration getJwt() {
        return jwt;
    }

    public void setJwt(JwtConfiguration jwt) {
        this.jwt = jwt;
    }

    public AwsS3Configuration getAwsS3() {
        return awsS3;
    }

    public void setAwsS3(AwsS3Configuration awsS3) {
        this.awsS3 = awsS3;
    }

    public List<String> getRedisReplicas() {
        return redisReplicas;
    }

    public void setRedisReplicas(List<String> redisReplicas) {
        this.redisReplicas = redisReplicas;
    }

    public PaymentConfiguration getPayment() {
        return payment;
    }

    public void setPayment(PaymentConfiguration payment) {
        this.payment = payment;
    }

    public EmailConfiguration getEmailConfiguration() {
        return email;
    }

    public void setEmail(EmailConfiguration email) {
        this.email = email;
    }
}
