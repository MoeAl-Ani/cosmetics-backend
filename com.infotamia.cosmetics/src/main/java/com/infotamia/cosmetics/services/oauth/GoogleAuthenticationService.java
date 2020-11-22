package com.infotamia.cosmetics.services.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.scribejava.core.model.*;
import com.github.scribejava.core.oauth.OAuth20Service;
import com.infotamia.access.AuthenticationProvider;
import com.infotamia.config.ConfigEnum;
import com.infotamia.config.ConfigServiceQualifier;
import com.infotamia.config.GoogleAuthenticationConfiguration;
import com.infotamia.exception.BaseErrorCode;
import com.infotamia.exception.ExceptionMessage;
import com.infotamia.exception.RestCoreException;
import com.infotamia.logger.LoggerFactory;
import com.infotamia.pojos.common.AppState;
import com.infotamia.pojos.common.user.GoogleExternalAccount;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * @author Mohammed Al-Ani
 */
@RequestScoped
@AuthService(value = AuthenticationProvider.GOOGLE)
public class GoogleAuthenticationService implements BaseOAuth20Service {

    @Inject
    private OAuth20Service service;
    @Inject
    @ConfigServiceQualifier(ConfigEnum.GOOGLE)
    private GoogleAuthenticationConfiguration googleConfig;
    @Inject
    private ObjectMapper mapper;
    @Inject
    @AuthState
    private AppState state;
    private final Logger logger = LoggerFactory.getLogger(GoogleAuthenticationService.class);

    @Override
    public String getAuthorizationUrl() {
        return service.getAuthorizationUrl(state.getDigitalSignature());
    }

    @Override
    public String getAccessToken(String code) {
        if (code == null || code.isEmpty()) {
            throw new RestCoreException(new ExceptionMessage(
                    400,
                    BaseErrorCode.CODE_WAS_WRONG,
                    "code can not be null or empty"));
        }

        try {
            OAuth2AccessToken accessToken = service.getAccessToken(code);
            return accessToken.getAccessToken();
        } catch (OAuth2AccessTokenErrorResponse e) {
            throw new RestCoreException(new ExceptionMessage(
                    400, BaseErrorCode.ACCESS_TOKEN_REQUEST_FAILED, "Access token request has failed" + e.getMessage()));
        } catch (InterruptedException | ExecutionException | IOException e) {
            throw new RestCoreException(500, BaseErrorCode.ACCESS_TOKEN_REQUEST_FAILED, e.getMessage());
        }
    }

    @Override
    public GoogleExternalAccount getAccountDetails(String accessToken) {
        if (accessToken == null || accessToken.isEmpty()) {
            throw new RestCoreException(new ExceptionMessage(
                    400,
                    BaseErrorCode.INVALID_ACCESS_TOKEN,
                    "Access token must not be null or empty"));
        }
        Response response = executeRequest(accessToken, new OAuthRequest(Verb.GET, googleConfig.getProfileUrl()));
        if (response.isSuccessful()) {
            GoogleExternalAccount externalAccount = parseResponse(response);
            externalAccount.setAccessToken(accessToken);
            return externalAccount;
        } else {
            throw new RestCoreException(new ExceptionMessage(
                    400,
                    BaseErrorCode.LOGIN_STEP_2_FAILED,
                    "Request has failed: " + response.getMessage()));
        }
    }

    private Response executeRequest(String accessToken, OAuthRequest request) {

        try {
            service.signRequest(accessToken, request);
            return service.execute(request);
        } catch (Exception e) {
            throw new RestCoreException(new ExceptionMessage(
                    400,
                    BaseErrorCode.SERVER_ERROR,
                    "Can't execute request" + e.getMessage()));
        }
    }

    private GoogleExternalAccount parseResponse(com.github.scribejava.core.model.Response response) {
        try {
            return mapper.readValue(response.getBody(), GoogleExternalAccount.class);
        } catch (IOException e) {
            logger.error("Parsing external exception", e);
            return null;
        }
    }
}
