package com.infotamia.cosmetics.factory;


import javax.enterprise.inject.spi.CDI;

import java.lang.annotation.Annotation;

/**
 * Generic CDI factory to assist on creating beans.
 *
 * @author Mohammed Al-Ani
 */
public class AbstractCDIFactory {

    /**
     * create instance with given qualifier. useful when there is an interface and many concrete implementations.
     *
     * @param instanceClazz
     * @param qualifierClazz
     * @param <T>
     * @return T requested instance.
     */
    public static <T> T select(Class<T> instanceClazz, Class<? extends Annotation> qualifierClazz) {
        if (qualifierClazz == null && instanceClazz == null) {
            return null;
        }
        if (qualifierClazz == null) {
            return CDI.current().select(instanceClazz).get();
        }

        Annotation annotation = instanceClazz.getAnnotation(qualifierClazz);
        return CDI.current().select(instanceClazz, annotation).get();
    }

    /**
     * create instance for the given class.
     *
     * @param instanceClazz
     * @param <T>
     * @return T requested instance.
     */
    public static <T> T select(Class<T> instanceClazz) {
        return select(instanceClazz, null);
    }

}
