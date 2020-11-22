package com.infotamia.cosmetics.factory;

import com.infotamia.access.AbstractPrincipal;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * CDI factory for providing user principal in request scope.
 *
 * @author Mohammed Al-Ani
 */
@RequestScoped
public class UserPrincipalFactory {
    @Context
    private SecurityContext securityContext;


    @Produces
    public AbstractPrincipal provide() {
        return (AbstractPrincipal) securityContext.getUserPrincipal();
    }
}
