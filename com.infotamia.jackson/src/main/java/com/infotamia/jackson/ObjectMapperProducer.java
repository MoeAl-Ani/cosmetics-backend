package com.infotamia.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * Jackson mapper producer.
 *
 * @author Mohammed Al-Ani
 */
@ApplicationScoped
public class ObjectMapperProducer {

    private ObjectMapper mapper;

    @Produces
    public ObjectMapper createObjectMapper() {
        if (mapper == null) {
            mapper = new ObjectMapper()
                    .enable(SerializationFeature.INDENT_OUTPUT)
                    .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                    .enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .registerModule(new LocalDateTimeModule())
                    .findAndRegisterModules();
        }
        return mapper.copy();
    }

}
