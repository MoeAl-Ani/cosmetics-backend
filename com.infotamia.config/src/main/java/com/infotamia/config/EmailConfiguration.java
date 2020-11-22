package com.infotamia.config;

/**
 * @author Mohammed Al-Ani
 **/
public class EmailConfiguration {
    public EmailConfiguration() {
        //
    }

    private String smtpHost;
    private String userName;
    private String password;
    private String port;
    private boolean debug;

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
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

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public String toString() {
        return super.toString();
    }
}
