package com.infotamia.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * general REST runtime exception mapper.
 *
 * @author Mohammed Al-Ani
 */
@Provider
public class RestCoreExceptionMapper implements ExceptionMapper<RestCoreException> {

    @Override
    public Response toResponse(RestCoreException e) {
        Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
        logger.error("EXCEPTION LOGGING : ", e);
        e.setStackTrace(new StackTraceElement[0]);
        return Response.status(e.getStatusCode()).entity(e).type(MediaType.APPLICATION_JSON).build();
    }
}
