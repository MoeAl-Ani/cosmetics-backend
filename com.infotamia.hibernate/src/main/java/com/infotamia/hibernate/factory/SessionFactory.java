package com.infotamia.hibernate.factory;

import com.infotamia.hibernate.common.ApplicationPersistentUnit;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import org.hibernate.Session;
import org.hibernate.internal.SessionImpl;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


/**
 * Hibernate Session factory.
 * currently it is hard coded unit name.
 * TODO bind the unit name to user session or {@link ThreadLocal} to support multi tenancy.
 *
 * @author Mohammed Al-Ani
 */
@RequestScoped
public class SessionFactory {

    @Inject
    InfotamiaEntityManagerFactory managerFactory;
    @Inject
    private InitialContext context;
    private Session session;

    @Produces
    public synchronized Session getSession() {
        try {
            // TODO this could be set in the jwt and fetch the target unit from CDI or ThreadContext.
            String unitName = "cosmetics";
            EntityManagerFactory factory;
            if (managerFactory.getFactoriesHolder().get(unitName) == null) {
                if (context == null) throw new RuntimeException("application context was null");
                DataSource ds = (DataSource) context.lookup("jdbc/CosmeticsDs");
                ApplicationPersistentUnit persistentUnit = new ApplicationPersistentUnit(unitName, ds);
                factory = new EntityManagerFactoryBuilderImpl(
                        new PersistenceUnitInfoDescriptor(persistentUnit), null)
                        .build();
                managerFactory.getFactoriesHolder().put(unitName, factory);
            }
            if (session != null) return session;
            session = managerFactory.getFactoriesHolder().get(unitName).createEntityManager().unwrap(Session.class);
            ((SessionImpl) session).connection().setCatalog("cosmetics");
            return session;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
