package com.infotamia.cosmetics.filters;

import javax.ws.rs.NameBinding;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Manual name binding to resources that require object patch capability.
 *
 * @author Mohammed Al-Ani
 */
@NameBinding
@Retention(RUNTIME)
@Target({TYPE, METHOD})
public @interface PatchRequired {
}
