package com.infotamia.cosmetics.transaction;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import javax.persistence.EntityManager;

/**
 * transaction interceptor implementation.
 *
 * @author Mohammed Al-Ani
 */
@CosmeticsTransactional
@Interceptor
@Priority(Interceptor.Priority.APPLICATION - 50)
public class CosmeticsTransactionInterceptorImpl {


    @Inject
    EntityManager entityManager;

    public CosmeticsTransactionInterceptorImpl() {
        //
    }

    @AroundInvoke
    public Object transactionInterceptor(InvocationContext ctx) throws Exception {
        entityManager.getTransaction().begin();
        try {
            Object object = ctx.proceed();
            entityManager.getTransaction().commit();
            return object;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        }
    }
}
