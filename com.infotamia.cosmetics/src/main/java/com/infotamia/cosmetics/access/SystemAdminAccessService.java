package com.infotamia.cosmetics.access;

import com.infotamia.access.AccessService;
import com.infotamia.base.rest.AbstractResourceHandler;

/**
 * @author Mohammed Al-Ani
 **/
public class SystemAdminAccessService extends AccessService {
    public SystemAdminAccessService() {
    }

    @Override
    public Boolean hasAccessToResource(Class<? extends AbstractResourceHandler> resourceClass) {
        return true;
    }

    public String toString() {
        return super.toString();
    }
}
