package com.infotamia.cosmetics.filters;

import com.infotamia.access.AbstractPrincipal;
import com.infotamia.access.BaseSecurityContext;
import com.infotamia.base.rest.AbstractResourceHandler;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Authorization filters used to process requests after the Authentication filter.
 * Check if the user principal has access to the targeted resource using the {@link com.infotamia.access.AccessService}
 *
 * @author Mohammed Al-Ani
 */
@SuppressWarnings("unchecked")
@Provider
@Priority(Priorities.AUTHORIZATION)
public class AuthorizationRequestFilter implements ContainerRequestFilter, ContainerResponseFilter {
    private static final Logger logger = LogManager.getLogger(AuthorizationRequestFilter.class.getSimpleName());

    @Override
    public void filter(ContainerRequestContext requestContext) {

        if (SkipFilterUtils.shouldSkip(requestContext.getUriInfo().getRequestUri().getPath())) {
            return;
        }

        BaseSecurityContext cosmeticsSecurityContext = (BaseSecurityContext) requestContext.getSecurityContext();
        AbstractPrincipal user = (AbstractPrincipal) cosmeticsSecurityContext.getUserPrincipal();
        validateMatchedResourcesWithUserPrincipal(requestContext, user);
    }

    private void validateMatchedResourcesWithUserPrincipal(ContainerRequestContext requestContext,
                                                           AbstractPrincipal user) {

        requestContext.getUriInfo().getMatchedResources().stream().filter(h -> h instanceof AbstractResourceHandler)
                .forEach(h -> {
                    AbstractResourceHandler handler = (AbstractResourceHandler) h;
                    Class clazz = handler.getClass();
                    if (handler.getClass().isSynthetic()) {
                        clazz = clazz.getSuperclass();
                    }
                    if ((user == null || user.getAccessService() == null) || !user.getAccessService().hasAccessToResource(clazz)) {
                        logger.debug("Aborting request: principal is of type unknown");
                        requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).build());
                    }
                });
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        // TODO set cookies in future for logged users.
    }
}
