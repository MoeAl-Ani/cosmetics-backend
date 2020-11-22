package com.infotamia.access;

import javax.ws.rs.core.SecurityContext;

import java.security.Principal;

/**
 * Base security class for providing security related information.
 * extending this class is possible in case the base info is not enough.
 *
 * @author Mohammed Al-Ani
 */
public class BaseSecurityContext implements SecurityContext {

    private final AbstractPrincipal abstractPrincipal;

    public BaseSecurityContext(AbstractPrincipal abstractPrincipal) {
        super();
        this.abstractPrincipal = abstractPrincipal;
    }

    @Override
    public Principal getUserPrincipal() {
        return abstractPrincipal;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public boolean isSecure() {
        return true;
    }

    @Override
    public String getAuthenticationScheme() {
        return "bearer";
    }
}
