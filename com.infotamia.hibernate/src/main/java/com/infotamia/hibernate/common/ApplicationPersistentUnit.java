package com.infotamia.hibernate.common;

import com.infotamia.hibernate.utils.ClassReflectionUtils;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * Persistence unit bean. no need for persistent.xml file.
 *
 * @author Mohammed Al-Ani
 */
public class ApplicationPersistentUnit implements PersistenceUnitInfo {

    private final String unitName;
    private final DataSource dataSource;

    public ApplicationPersistentUnit(String unitName, DataSource dataSource) {
        this.unitName = unitName;
        this.dataSource = dataSource;
    }

    @Override
    public String getPersistenceUnitName() {
        return unitName;
    }

    @Override
    public String getPersistenceProviderClassName() {
        return "org.hibernate.jpa.HibernatePersistenceProvider";
    }

    @Override
    public PersistenceUnitTransactionType getTransactionType() {
        return PersistenceUnitTransactionType.RESOURCE_LOCAL;
    }

    @Override
    public DataSource getJtaDataSource() {
        return dataSource;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return dataSource;
    }

    @Override
    public List<String> getMappingFileNames() {
        return null;
    }

    @Override
    public List<URL> getJarFileUrls() {
        return null;
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    @Override
    public List<String> getManagedClassNames() {
        return ClassReflectionUtils.getEntitiesNames();
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return false;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return SharedCacheMode.NONE;
    }

    @Override
    public ValidationMode getValidationMode() {
        return ValidationMode.AUTO;
    }

    @Override
    public Properties getProperties() {
        Properties p = new Properties();
        p.put("hibernate.dialect", "org.hibernate.spatial.dialect.mysql.MySQL8SpatialDialect");
        p.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        return p;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        return null;
    }

    @Override
    public ClassLoader getClassLoader() {
        return ApplicationPersistentUnit.class.getClassLoader();
    }

    @Override
    public void addTransformer(ClassTransformer transformer) {

    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}

