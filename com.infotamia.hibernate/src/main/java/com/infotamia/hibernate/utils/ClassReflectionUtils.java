package com.infotamia.hibernate.utils;

import org.reflections.Reflections;

import javax.persistence.Entity;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for reflecting entities and attach them to the persistent bean.
 *
 * @author Mohammed Al-Ani
 */
public class ClassReflectionUtils {

    private ClassReflectionUtils() {

    }

    public static List<String> getEntitiesNames() {

        return new Reflections("com.infotamia.pojos")
                .getTypesAnnotatedWith(Entity.class)
                .stream()
                .map(Class::getName)
                .collect(Collectors.toList());

    }
}
