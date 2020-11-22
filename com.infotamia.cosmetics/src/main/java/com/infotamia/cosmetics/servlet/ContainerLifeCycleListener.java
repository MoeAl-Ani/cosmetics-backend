package com.infotamia.cosmetics.servlet;

import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;

/**
 * Servlet life cycle listener.
 *
 * @author Mohammed Al-Ani
 */
public class ContainerLifeCycleListener implements ContainerLifecycleListener {
    @Override
    public void onStartup(Container container) {
        // add startup logic
    }

    @Override
    public void onReload(Container container) {
        // add reload logic
    }

    @Override
    public void onShutdown(Container container) {
        // add shutdown logic
    }
}
