package com.infotamia.base.params;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Generic http param converter provider.
 *
 * @author Mohammed Al-Ani
 */
@Provider
public class ApplicationParamConverterProvider implements ParamConverterProvider {
    @Override
    @SuppressWarnings("unchecked")
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        if (rawType.equals(LocalDateTime.class)) {
            final LocalDateTimeParamConverter localDateTimeParamConverter = new LocalDateTimeParamConverter();
            return (ParamConverter<T>) localDateTimeParamConverter;

        } else if (rawType.equals(LocalDate.class)) {
            final LocalDateParamConverter localDateParamConverter = new LocalDateParamConverter();
            return (ParamConverter<T>) localDateParamConverter;
        }
        return null;
    }
}
