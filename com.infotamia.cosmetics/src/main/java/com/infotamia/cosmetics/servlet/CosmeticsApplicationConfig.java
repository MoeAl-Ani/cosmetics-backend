package com.infotamia.cosmetics.servlet;

import com.infotamia.base.params.ApplicationParamConverterProvider;
import com.infotamia.exception.BaseExceptionMapper;
import com.infotamia.exception.RestCoreExceptionMapper;
import com.infotamia.exception.RestCoreExceptionThrowableMapper;
import com.infotamia.exception.ValidationExceptionMapper;
import com.infotamia.jackson.ObjectMapperContextResolver;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

/**
 * Cosmetic application is configured here.
 *
 * @author Mohammed Al-Ani
 */
@ApplicationPath("/cosmetics/api/v1/")
public class CosmeticsApplicationConfig extends ResourceConfig {
    public CosmeticsApplicationConfig() {
        packages("com.infotamia.cosmetics.rest", "com.infotamia.cosmetics.filters");
        register(ObjectMapperContextResolver.class);
        register(RestCoreExceptionMapper.class);
        register(RestCoreExceptionThrowableMapper.class);
        register(BaseExceptionMapper.class);
        register(ValidationExceptionMapper.class);
        register(JacksonFeature.class);
        register(ApplicationParamConverterProvider.class);
        property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        property(ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);
        register(ContainerLifeCycleListener.class);
    }
}
