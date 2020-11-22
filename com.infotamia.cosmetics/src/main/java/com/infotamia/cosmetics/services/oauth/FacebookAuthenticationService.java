package com.infotamia.cosmetics.services.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.apis.facebook.FacebookAccessTokenErrorResponse;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.infotamia.access.AuthenticationProvider;
import com.infotamia.config.ConfigEnum;
import com.infotamia.config.ConfigServiceQualifier;
import com.infotamia.config.FacebookAuthenticationConfiguration;
import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.ExceptionMessage;
import com.infotamia.exception.RestCoreException;
import com.infotamia.logger.LoggerFactory;
import com.infotamia.pojos.common.AppState;
import com.infotamia.pojos.common.user.ExternalAccount;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Facebook authentication service.
 *
 * @author Mohammed Al-Ani
 */
@RequestScoped
@AuthService(value = AuthenticationProvider.FACEBOOK)
public class FacebookAuthenticationService implements BaseOAuth20Service {
    private final Logger logger = LoggerFactory.getLogger(FacebookAuthenticationService.class);
    @Inject
    private OAuth20Service service;
    @Inject
    @ConfigServiceQualifier(value = ConfigEnum.FACEBOOK)
    private FacebookAuthenticationConfiguration facebookConfig;
    @Inject
    private ObjectMapper mapper;
    @Inject
    @AuthState
    private AppState state;

    @Override
    public String getAuthorizationUrl() {
        return service.getAuthorizationUrl(state.getDigitalSignature());
    }

    @Override
    public String getAccessToken(String code) {
        if (code == null || code.isEmpty()) {
            throw new RestCoreException(
                    new ExceptionMessage(
                            400,
                            BaseErrorCode.CODE_WAS_WRONG,
                            "code can not be null or empty"));
        }

        try {
            OAuth2AccessToken accessToken = service.getAccessToken(code);
            return accessToken.getAccessToken();
        } catch (OAuth2AccessTokenErrorResponse | FacebookAccessTokenErrorResponse e) {
            throw new RestCoreException(
                    new ExceptionMessage(
                            400,
                            BaseErrorCode.ACCESS_TOKEN_REQUEST_FAILED,
                            "Access token request has failed" + e.getMessage()));
        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new RestCoreException(500, BaseErrorCode.ACCESS_TOKEN_REQUEST_FAILED, e.getMessage(), e.getMessage());
        }
    }


    @Override
    public ExternalAccount getAccountDetails(String accessToken) {

        if (accessToken == null || accessToken.isEmpty()) {
            throw new RestCoreException(
                    new ExceptionMessage(
                            500,
                            BaseErrorCode.INVALID_ACCESS_TOKEN,
                            "Access token must not be null or empty"));
        }

        Response response = executeRequest(accessToken, new OAuthRequest(Verb.GET, facebookConfig.getProfileUrl()));
        if (response.isSuccessful()) {
            ExternalAccount externalAccount = parseResponse(response);
            externalAccount.setAccessToken(accessToken);
            return externalAccount;
        } else {
            throw new RestCoreException(
                    new ExceptionMessage(
                            500,
                            BaseErrorCode.LOGIN_STEP_2_FAILED,
                            "Request has failed: " + response.getMessage()));
        }
    }

    private Response executeRequest(String accessToken, OAuthRequest request) {

        try {
            service.signRequest(accessToken, request);
            return service.execute(request);
        } catch (Exception e) {
            throw new RestCoreException(
                    new ExceptionMessage(
                            500,
                            BaseErrorCode.SERVER_ERROR,
                            "Can't execute request" + e.getMessage()));
        }
    }

    private ExternalAccount parseResponse(com.github.scribejava.core.model.Response response) {
        try {
            return mapper.readValue(response.getBody(), ExternalAccount.class);
        } catch (IOException e) {
            logger.error("Parsing external exception", e);
            throw new RestCoreException(500, BaseErrorCode.SERVER_ERROR, "parsing account failed");
        }
    }
}
