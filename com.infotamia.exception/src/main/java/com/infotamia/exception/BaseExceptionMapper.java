package com.infotamia.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * general Rest checked exception mapper.
 *
 * @author Mohammed Al-Ani
 */
@Provider
public class BaseExceptionMapper implements ExceptionMapper<BaseException> {

    @Override
    public Response toResponse(BaseException exception) {
        RestCoreException restCoreException = null;
        if (exception instanceof ItemNotFoundException | exception instanceof IncorrectParameterException) {
            restCoreException = new RestCoreException(
                    400,
                    exception.getErrorCode(),
                    exception.getMessage(),
                    exception.getMessage());
        } else if (exception instanceof OperationNotAllowedException) {
            restCoreException = new RestCoreException(
                    403,
                    exception.getErrorCode(),
                    exception.getMessage(),
                    exception.getMessage());
        } else if (exception instanceof DataCorruptedException | exception instanceof UnknownException) {
            restCoreException = new RestCoreException(
                    500,
                    exception.getErrorCode(),
                    exception.getMessage(),
                    exception.getMessage());
        } else {
            restCoreException = new RestCoreException(
                    500,
                    BaseErrorCode.UNKNOWN,
                    exception.getMessage(),
                    exception.getMessage());
        }
        return new RestCoreExceptionMapper().toResponse(restCoreException);
    }
}
