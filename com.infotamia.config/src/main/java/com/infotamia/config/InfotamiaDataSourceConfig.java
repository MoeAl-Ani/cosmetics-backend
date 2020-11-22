package com.infotamia.config;

/**
 * Datasource configuration.
 *
 * @author Mohammed Al-Ani
 */
public class InfotamiaDataSourceConfig {

    private String jndiName;
    private String driverClass;
    private String connectionUrl;
    private String userName;
    private String password;
    private Boolean autoCommit;
    private Integer connectionTimeout;
    private Integer idleTimeout;
    private Integer maxLifetime;
    private String name;
    private Integer minIdle;
    private Integer maxSize;
    private Integer validationTimeout;
    private Integer leakDetectionThreshold;

    public InfotamiaDataSourceConfig() {
    }

    public String getJndiName() {
        return jndiName;
    }

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }

    public void setConnectionUrl(String connectionUrl) {
        this.connectionUrl = connectionUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(Boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(Integer idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public Integer getMaxLifetime() {
        return maxLifetime;
    }

    public void setMaxLifetime(Integer maxLifetime) {
        this.maxLifetime = maxLifetime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(Integer maxSize) {
        this.maxSize = maxSize;
    }

    public Integer getValidationTimeout() {
        return validationTimeout;
    }

    public void setValidationTimeout(Integer validationTimeout) {
        this.validationTimeout = validationTimeout;
    }

    public Integer getLeakDetectionThreshold() {
        return leakDetectionThreshold;
    }

    public void setLeakDetectionThreshold(Integer leakDetectionThreshold) {
        this.leakDetectionThreshold = leakDetectionThreshold;
    }
}
