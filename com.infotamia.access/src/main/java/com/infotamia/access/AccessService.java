package com.infotamia.access;

import com.infotamia.base.rest.AbstractResourceHandler;

/**
 * Base Access service.
 * provide user crud for a feature.
 * provide privilege access for resources.
 * extending service will be instantiated during the login process.
 *
 * @author Mohammed Al-Ani
 */
public abstract class AccessService {

    protected Boolean globalCreateAccess;
    protected Boolean globalReadAccess;
    protected Boolean globalUpdateAccess;
    protected Boolean globalDeleteAccess;

    public AccessService() {
        globalCreateAccess = false;
        globalReadAccess = false;
        globalUpdateAccess = false;
        globalDeleteAccess = false;
    }

    public abstract Boolean hasAccessToResource(Class<? extends AbstractResourceHandler> resourceClass);

    public Boolean getGlobalCreateAccess() {
        return globalCreateAccess;
    }

    public Boolean getGlobalReadAccess() {
        return globalReadAccess;
    }

    public Boolean getGlobalUpdateAccess() {
        return globalUpdateAccess;
    }

    public Boolean getGlobalDeleteAccess() {
        return globalDeleteAccess;
    }
}
