package com.infotamia.cosmetics.filters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infotamia.cosmetics.patch.ObjectPatchImpl;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import org.glassfish.jersey.message.MessageBodyWorkers;

import java.io.IOException;

/**
 * Provide support for object patching.
 * at the moment the interceptor uses only around read invocation.
 *
 * @author Mohammed Al-Ani
 */
@Provider
@PatchRequired
@RequestScoped
public class PatchReader implements ReaderInterceptor {
    @Context
    ResourceInfo resourceInfo;
    private UriInfo info;
    private MessageBodyWorkers workers;
    @Inject
    private
    ObjectMapper mapper;

    public PatchReader() {
    }

    @Context
    public void setInfo(UriInfo info) {
        this.info = info;
    }

    @Context
    public void setWorkers(MessageBodyWorkers workers) {
        this.workers = workers;
    }

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException {
        JsonNode patchAsNode = mapper.readValue(context.getInputStream(), JsonNode.class);
        return new ObjectPatchImpl(patchAsNode, mapper);
    }
}
