package com.infotamia.cosmetics.filters;

import javax.annotation.Priority;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import java.io.IOException;

/**
 * Cross Origin Resource Sharing filter has the highest priority.
 * All Web Application requests first pass through this filter for process the cors.
 * Add the required optional and non optional headers.
 *
 * @author Mohammed Al-Ani
 */
@Provider
@Priority(500)
@PreMatching
public class CorsFilter implements ContainerResponseFilter, ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) {

    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        MultivaluedMap<String, Object> headers = responseContext.getHeaders();
        if (requestContext.getMethod().equals(HttpMethod.OPTIONS)) {
            addOptionsHeaders(headers);
        } else {
            addNonOptionsHeaders(headers);
        }
    }

    private void addOptionsHeaders(MultivaluedMap<String, Object> headers) {
        addNonOptionsHeaders(headers);
        headers.add("Access-Control-Allow-Headers", "Content-Type, Authorization, X-Requested-With, jwt-auth-token");
        headers.add("Allow", "OPTIONS, GET, POST, PATCH, DELETE");
        headers.add("Access-Control-Expose-Headers", "X-Requested-With, Content-Type, Location");

    }

    private void addNonOptionsHeaders(MultivaluedMap<String, Object> headers) {
        headers.add("Access-Control-Allow-Credentials", "true");
        headers.add("Access-Control-Allow-Origin", "*");
        headers.add("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PATCH, DELETE");
        headers.add("Access-Control-Expose-Headers", "X-Requested-With, Content-Type, Location");
    }

}
