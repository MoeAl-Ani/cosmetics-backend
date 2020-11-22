package com.infotamia.base.params;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ParamConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Local date time param converter.
 *
 * @author Mohammed Al-Ani
 */
public class LocalDateTimeParamConverter implements ParamConverter<LocalDateTime> {
    @Override
    public LocalDateTime fromString(String value) {
        if (value == null) return null;
        try {
            LocalDateTime parsed = LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return parsed.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
        } catch (Exception e) {
            throw new WebApplicationException(e);
        }
    }

    @Override
    public String toString(LocalDateTime value) {
        return null;
    }
}
