package com.infotamia.cosmetics.application;

import com.infotamia.cosmetics.servlet.CosmeticsApplicationConfig;
import com.infotamia.config.InfotamiaDataSourceConfig;
import com.infotamia.logger.LoggerFactory;
import com.infotamia.utils.ConfigurationUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.ws.rs.core.UriBuilder;
import org.apache.logging.log4j.Logger;
import org.eclipse.jetty.plus.jndi.Resource;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;

import java.net.URI;
import java.util.List;

/**
 * Main application for the backend.
 * All server extensions must be added here.
 *
 * @author Mohammed Al-Ani
 */
public class CosmeticApplication {

    private static final Logger logger = LoggerFactory.getLogger(CosmeticApplication.class);

    public static void main(String[] args) throws Exception {
        URI baseUri = UriBuilder.fromUri("http://127.0.0.1/").port(8080).build();
        Server server = JettyHttpContainerFactory.createServer(baseUri, false);
        for(Connector y : server.getConnectors()) {
            for(ConnectionFactory x  : y.getConnectionFactories()) {
                if(x instanceof HttpConnectionFactory) {
                    ((HttpConnectionFactory)x).getHttpConfiguration().setSendServerVersion(false);
                }
            }
        }
        ServletContextHandler ctx = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        ctx.setContextPath("/*");
        ServletContainer jersey = new ServletContainer(new CosmeticsApplicationConfig());
        ServletHolder holder = new ServletHolder(jersey);
        ctx.addEventListener(new Listener());
        ctx.addServlet(holder, "/cosmetics/api/v1/*");
        server.setHandler(ctx);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                logger.info("shutting down jetty");
                server.stop();
            } catch (Exception e) {
                logger.error("Exception: ", e);
            }
        }));


        server.start();
        List<InfotamiaDataSourceConfig> infotamiaDataSourceConfigs = ConfigurationUtils.loadDataSourceConfig();
        for (InfotamiaDataSourceConfig dsConfig : infotamiaDataSourceConfigs) {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(dsConfig.getDriverClass());
            hikariConfig.setJdbcUrl(dsConfig.getConnectionUrl());
            hikariConfig.setUsername(dsConfig.getUserName());
            hikariConfig.setPassword(dsConfig.getPassword());
            hikariConfig.setPoolName(dsConfig.getName());
            hikariConfig.setLeakDetectionThreshold(dsConfig.getLeakDetectionThreshold());
            hikariConfig.setConnectionTimeout(dsConfig.getConnectionTimeout());
            hikariConfig.setIdleTimeout(dsConfig.getIdleTimeout());
            hikariConfig.setMinimumIdle(dsConfig.getMinIdle());
            hikariConfig.setMaximumPoolSize(dsConfig.getMaxSize());
            HikariDataSource ds = new HikariDataSource(hikariConfig);
            new Resource(dsConfig.getJndiName(), ds);
        }
    }
}
