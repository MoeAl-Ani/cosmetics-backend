package com.infotamia.cosmetics.services.oauth;

import com.github.scribejava.apis.FacebookApi;
import com.github.scribejava.apis.GoogleApi20;
import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.infotamia.access.AbstractPrincipal;
import com.infotamia.access.SessionType;
import com.infotamia.cosmetics.rest.user.login.FacebookCustomerLoginResource;
import com.infotamia.cosmetics.rest.user.UserResource;
import com.infotamia.config.ConfigEnum;
import com.infotamia.config.ConfigServiceQualifier;
import com.infotamia.config.FacebookAuthenticationConfiguration;
import com.infotamia.config.GoogleAuthenticationConfiguration;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * General OAuth20 factory
 *
 * @author Mohammed Al-Ani
 */
@RequestScoped
public class OAuth20ServiceFactory {

    @Inject
    AbstractPrincipal abstractPrincipal;
    @Context
    ResourceInfo resourceInfo;
    @Context
    UriInfo uriInfo;
    @Inject
    @ConfigServiceQualifier(value = ConfigEnum.FACEBOOK)
    private FacebookAuthenticationConfiguration facebookConfig;
    @Inject
    @ConfigServiceQualifier(ConfigEnum.GOOGLE)
    private GoogleAuthenticationConfiguration googleConfig;

    /**
     * provide OAuth20Service based on the requested login resource.
     *
     * @return OAuth20Service
     */
    @Produces
    public OAuth20Service provide() {
        if (resourceInfo.getResourceClass().equals(FacebookCustomerLoginResource.class)) {
            return getUserFacebook();
        } else if (resourceInfo.getResourceClass().equals(UserResource.class)) {
            if (SessionType.CUSTOMER.equals(abstractPrincipal.getSessionType())) {
                switch (abstractPrincipal.getAuthenticationProvider()) {
                    case GOOGLE:
                        return getUserGoogle();
                    case FACEBOOK:
                        return getUserFacebook();
                }
            } else {
                switch (abstractPrincipal.getAuthenticationProvider()) {
                    case GOOGLE:
                        return getAdminGoogle();
                    case FACEBOOK:
                        return getAdminFacebook();
                }
            }
        }
        return null;
    }

    private OAuth20Service getAdminFacebook() {
        return new ServiceBuilder(facebookConfig.getClientId())
                .apiSecret(facebookConfig.getClientSecret())
                .defaultScope(facebookConfig.getScope())
                .callback(facebookConfig.getCallbackAdminUrl())
                .build(FacebookApi.instance());
    }

    private OAuth20Service getAdminGoogle() {
        return new ServiceBuilder(googleConfig.getClientId())
                .apiSecret(googleConfig.getClientSecret())
                .defaultScope(googleConfig.getScope())
                .callback(googleConfig.getCallbackAdminUrl())
                .build(GoogleApi20.instance());
    }

    private OAuth20Service getUserFacebook() {
        return new ServiceBuilder(facebookConfig.getClientId())
                .apiSecret(facebookConfig.getClientSecret())
                .defaultScope(facebookConfig.getScope())
                .callback(facebookConfig.getCallbackUrl())
                .build(FacebookApi.instance());
    }

    private OAuth20Service getUserGoogle() {
        return new ServiceBuilder(googleConfig.getClientId())
                .apiSecret(googleConfig.getClientSecret())
                .defaultScope(googleConfig.getScope())
                .callback(googleConfig.getCallbackUrl())
                .build(GoogleApi20.instance());
    }
}
