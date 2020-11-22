package com.infotamia.config;

import com.infotamia.utils.ConfigurationUtils;
import javax.enterprise.context.ApplicationScoped;


/**
 * Application configuration provider.
 *
 * @author Mohammed Al-Ani
 */
@ApplicationScoped
public class ConfigProvider {
    private final ConfigBean config;


    public ConfigProvider() {
        // load configs
        this.config = ConfigurationUtils.loadApplicationServerConfiguration();
    }

    public FacebookAuthenticationConfiguration getFacebookConfiguration() {
        return config.getAuthenticationConfig().getFacebook();
    }


    public GoogleAuthenticationConfiguration getGoogleConfiguration() {
        return config.getAuthenticationConfig().getGoogle();
    }


    public JwtConfiguration getJwt() {
        return config.getJwt();
    }

    public AwsS3Configuration getAwsS3() {
        return config.getAwsS3();
    }

    public PaymentConfiguration getPayment() {
        return config.getPayment();
    }
    public EmailConfiguration getEmailConfiguration() {
        return config.getEmailConfiguration();
    }

}


