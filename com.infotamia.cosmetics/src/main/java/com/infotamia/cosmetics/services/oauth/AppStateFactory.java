package com.infotamia.cosmetics.services.oauth;

import com.infotamia.jwt.services.JwtTokenService;
import com.infotamia.pojos.common.AppState;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import java.util.UUID;

/**
 * CDI factory for creating a jwt state used in the login a long size the XSRF token.
 *
 * @author Mohammed Al-Ani
 */
@RequestScoped
public class AppStateFactory {
    @Inject
    private JwtTokenService jwtTokenService;
    private AppState state;

    @Produces
    @AuthState
    public AppState provide() {
        if (state == null) {
            String uuid = UUID.randomUUID().toString();
            String digitalSignature = jwtTokenService.issueJwtToken(uuid);
            state = new AppState();
            state.setDigitalSignature(digitalSignature);
        }
        return state;
    }
}
