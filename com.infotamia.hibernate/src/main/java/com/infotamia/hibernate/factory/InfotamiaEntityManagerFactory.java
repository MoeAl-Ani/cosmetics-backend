package com.infotamia.hibernate.factory;

import javax.enterprise.context.ApplicationScoped;

import javax.persistence.EntityManagerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CDI factory for {@link EntityManagerFactory}
 *
 * @author Mohammed Al-Ani
 */
@ApplicationScoped
public class InfotamiaEntityManagerFactory {

    private final Map<String, EntityManagerFactory> factoriesHolder = new ConcurrentHashMap<>();

    public Map<String, EntityManagerFactory> getFactoriesHolder() {
        return factoriesHolder;
    }
}