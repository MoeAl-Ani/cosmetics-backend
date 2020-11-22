package com.infotamia.cosmetics.rest.user;

import com.infotamia.access.AbstractPrincipal;
import com.infotamia.access.AuthenticationProvider;
import com.infotamia.base.rest.AbstractResourceHandler;
import com.infotamia.cosmetics.services.oauth.AuthService;
import com.infotamia.cosmetics.services.oauth.BaseOAuth20Service;
import com.infotamia.cosmetics.services.user.UserLanguageService;
import com.infotamia.cosmetics.services.user.UserService;
import com.infotamia.cosmetics.transaction.CosmeticsTransactional;
import com.infotamia.exception.IncorrectParameterException;
import com.infotamia.exception.ItemNotFoundException;
import com.infotamia.exception.RestCoreException;
import com.infotamia.pojos.common.user.ExternalAccount;
import com.infotamia.pojos.common.user.GoogleExternalAccount;
import com.infotamia.pojos.entities.LanguageEntity;
import com.infotamia.pojos.entities.UserEntity;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Objects;


/**
 * @author Mohammed Al-Ani
 */
@Path("user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class UserResource implements AbstractResourceHandler {

    @Inject
    AbstractPrincipal user;
    @Inject
    UserService userService;
    @Inject
    UserLanguageService userLanguageService;
    @Inject
    @AuthService(value = AuthenticationProvider.FACEBOOK)
    private BaseOAuth20Service facebookAuthenticationService;
    @Inject
    @AuthService(value = AuthenticationProvider.GOOGLE)
    private BaseOAuth20Service googleAuthenticationService;


    @GET
    @Path("profile")
    public UserEntity getProfile() throws ItemNotFoundException {
        UserEntity userEntity = userService.findUserById(this.user.getId());

        AuthenticationProvider provider = user.getAuthenticationProvider();
        if (provider == null) return userEntity;
        String accessToken = user.getAuthenticationProviderAccessToken();
        String imageUrl = null;
        byte[] imageBlob = new byte[0];
        switch (provider) {
            case FACEBOOK:
                ExternalAccount accountDetails = (ExternalAccount) facebookAuthenticationService.getAccountDetails(accessToken);
                imageUrl = accountDetails.getPicture() == null ? null : accountDetails.getPicture().getData().getUrl();
                imageBlob = readImageFromUrl(imageUrl);
                break;
            case GOOGLE:
                GoogleExternalAccount googleExternalAccount = (GoogleExternalAccount) googleAuthenticationService.getAccountDetails(accessToken);
                imageUrl = googleExternalAccount.getPicture();
                break;
            default:
                imageUrl = "";
        }
        return userEntity;
    }

    private byte[] readImageFromUrl(String imageUrl) {
        try (InputStream in = new URL(Objects.requireNonNull(imageUrl)).openStream()) {
            return in.readAllBytes();
        } catch (IOException ignore) {
            return new byte[0];
        }
    }

    @PATCH
    @Path("/language/")
    @CosmeticsTransactional
    public Response patchUserPreferredLanguage(LanguageEntity language) {
        try {
            userLanguageService.updateUserLanguage(language);
            return Response.accepted().build();
        } catch (IncorrectParameterException e) {
            throw new RestCoreException(400, e.getErrorCode(), e.getMessage());
        }
    }
}
