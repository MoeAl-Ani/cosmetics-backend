package com.infotamia.base.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * base resource class for all resources.
 *
 * @author Mohammed Al-Ani
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface AbstractResourceHandler {
}
