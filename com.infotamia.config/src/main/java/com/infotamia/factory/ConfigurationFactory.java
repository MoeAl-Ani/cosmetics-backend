package com.infotamia.factory;

import com.infotamia.config.*;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * CDI configuration factory.
 *
 * @author Mohammed Al-Ani
 */
@ApplicationScoped
public class ConfigurationFactory {
    @Inject
    private ConfigProvider configProvider;
    private FacebookAuthenticationConfiguration facebookAuthenticationConfiguration;
    private GoogleAuthenticationConfiguration googleAuthenticationConfiguration;
    private JwtConfiguration jwtConfiguration;
    private AwsS3Configuration awsS3Configuration;
    private PayTrailConfiguration payTrailConfiguration;
    private EmailConfiguration emailConfiguration;

    @Produces
    @ConfigServiceQualifier(value = ConfigEnum.FACEBOOK)
    public FacebookAuthenticationConfiguration provideFacebookConfig() {
        if (facebookAuthenticationConfiguration == null) {
            facebookAuthenticationConfiguration = configProvider.getFacebookConfiguration();
        }
        return facebookAuthenticationConfiguration;
    }

    @Produces
    @ConfigServiceQualifier(value = ConfigEnum.GOOGLE)
    public GoogleAuthenticationConfiguration provideGoogleConfig() {
        if (googleAuthenticationConfiguration == null) {
            googleAuthenticationConfiguration = configProvider.getGoogleConfiguration();
        }
        return googleAuthenticationConfiguration;
    }

    @Produces
    @ConfigServiceQualifier(value = ConfigEnum.JWT)
    public JwtConfiguration provideJwtConfig() {
        if (jwtConfiguration == null) {
            jwtConfiguration = configProvider.getJwt();
        }
        return jwtConfiguration;
    }

    @Produces
    @ConfigServiceQualifier(value = ConfigEnum.AWS_S3)
    public AwsS3Configuration provideAwsS3Config() {
        if (awsS3Configuration == null) {
            awsS3Configuration = configProvider.getAwsS3();
        }
        return awsS3Configuration;
    }

    @Produces
    @ConfigServiceQualifier(value = ConfigEnum.PAY_TRAIL)
    public PayTrailConfiguration providePayTrailConfig() {
        if (payTrailConfiguration == null) {
            payTrailConfiguration = configProvider.getPayment().getPaytrail();
        }
        return payTrailConfiguration;
    }

    @Produces
    @ConfigServiceQualifier(value = ConfigEnum.EMAIL)
    public EmailConfiguration provideEmailConfig() {
        if (emailConfiguration == null) {
            emailConfiguration = configProvider.getEmailConfiguration();
        }
        return emailConfiguration;
    }
}
