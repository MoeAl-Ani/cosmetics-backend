package com.infotamia.cosmetics.services.oauth;

import com.infotamia.pojos.common.user.ExternalAccountAbstract;

/**
 * Base auth 2 service.
 *
 * @author Mohammed Al-Ani
 */
public interface BaseOAuth20Service {
    /**
     * returns the authorization url loaded from the provided application configurations.
     *
     * @return String
     */
    String getAuthorizationUrl();

    /**
     * returns the login access token.
     *
     * @param code
     * @return String
     */
    String getAccessToken(String code);

    /**
     * returns the logged in account details.
     *
     * @param accessToken
     * @return ExternalAccountAbstract
     */
    ExternalAccountAbstract getAccountDetails(String accessToken);

}
