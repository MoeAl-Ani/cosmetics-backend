package com.infotamia.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * General Logger factory.
 *
 * @author Mohammed Al-Ani
 */
public class LoggerFactory {
    private LoggerFactory() {
    }

    public static Logger getLogger(Class<?> clazz) {
        return LogManager.getLogger(clazz);
    }
}

