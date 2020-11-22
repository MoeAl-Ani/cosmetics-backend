package com.infotamia.exception;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.List;

/**
 * General validation exception mapper.
 *
 * @author Mohammed Al-Ani
 */
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        RestCoreException restCoreException = new RestCoreException(400, prepareMessage(exception));
        return new RestCoreExceptionMapper().toResponse(restCoreException);
    }

    private List<ExceptionMessage> prepareMessage(ConstraintViolationException exception) {
        List<ExceptionMessage> messages = new ArrayList<>();
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            ExceptionMessage error = new ExceptionMessage(400, BaseErrorCode.INVALID_PARAMETERS, cv.getMessage());
            messages.add(error);
        }
        return messages;
    }

}

