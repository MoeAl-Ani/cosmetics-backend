package com.infotamia.base.params;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ParamConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Local date param converter.
 *
 * @author Mohammed Al-Ani
 */
public class LocalDateParamConverter implements ParamConverter<LocalDate> {
    @Override
    public LocalDate fromString(String value) {
        if (value == null) return null;
        try {
            return LocalDate.parse(value, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            throw new WebApplicationException(e);
        }
    }

    @Override
    public String toString(LocalDate value) {
        return null;
    }
}
