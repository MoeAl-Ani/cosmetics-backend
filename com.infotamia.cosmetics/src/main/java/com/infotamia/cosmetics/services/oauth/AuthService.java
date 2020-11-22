package com.infotamia.cosmetics.services.oauth;

import com.infotamia.access.AuthenticationProvider;
import javax.inject.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Qualifier for authentication service provider.
 *
 * @author Mohammed Al-Ani
 */
@Qualifier
@Retention(RUNTIME)
@Target({TYPE, METHOD, FIELD, PARAMETER})
public @interface AuthService {
    AuthenticationProvider value();
}
