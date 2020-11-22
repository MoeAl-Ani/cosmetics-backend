package com.infotamia.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author Mohammed Al-Ani
 */
public class LocalDateTimeModule extends SimpleModule {

    public LocalDateTimeModule() {
        addSerializer(LocalDate.class, new LocalDateSerializer());
        addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        addDeserializer(LocalDate.class, new LocalDateDeserializer());
        addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        addDeserializer(LocalTime.class, new LocalTimeDeserializer());
        addSerializer(LocalTime.class, new LocalTimeSerializer());
    }
}
