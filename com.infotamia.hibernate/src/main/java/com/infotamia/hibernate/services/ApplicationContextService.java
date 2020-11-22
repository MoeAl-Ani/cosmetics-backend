package com.infotamia.hibernate.services;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Mohammed Al-Ani
 */
@ApplicationScoped
public class ApplicationContextService {

    private InitialContext context;

    public ApplicationContextService() {
    }

    @Produces
    public InitialContext provide() {
        if (context == null) {
            try {
                context = new InitialContext();
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return context;
    }
}
