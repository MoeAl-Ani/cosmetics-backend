package com.infotamia.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * general REST throwable exception mapper.
 *
 * @author Mohammed Al-Ani
 */
@Provider
public class RestCoreExceptionThrowableMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        int statusCode = 500;
        if (exception instanceof WebApplicationException) {
            statusCode = ((WebApplicationException) exception).getResponse().getStatus();
        }
        RestCoreException restCoreException = new RestCoreException(statusCode, BaseErrorCode.UNKNOWN, exception.getMessage());
        return new RestCoreExceptionMapper().toResponse(restCoreException);
    }
}
