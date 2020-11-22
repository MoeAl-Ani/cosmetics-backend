package com.infotamia.cosmetics.transaction;


import javax.interceptor.InterceptorBinding;

import java.lang.annotation.*;

/**
 * Transaction interceptor annotation.
 *
 * @author Mohammed Al-Ani
 */
@Inherited
@InterceptorBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface CosmeticsTransactional {
}
