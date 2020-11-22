package com.infotamia.cosmetics.provider;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author Mohammed Al-Ani
 **/
@RequestScoped
public class UriInfoProvider {
    public UriInfoProvider() {
        //
    }
    @Context
    private UriInfo uriInfo;

    public UriInfo provide() {
        return uriInfo;
    }
}
