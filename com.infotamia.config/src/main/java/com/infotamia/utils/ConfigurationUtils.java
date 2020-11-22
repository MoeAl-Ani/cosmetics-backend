package com.infotamia.utils;

import com.infotamia.config.ConfigBean;
import com.infotamia.config.InfotamiaDataSourceConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for loading application configurations.
 *
 * @author Mohammed Al-Ani
 */
public class ConfigurationUtils {

    private static final Logger logger = LogManager.getLogger(ConfigurationUtils.class.getSimpleName());

    private ConfigurationUtils() {

    }

    public static List<InfotamiaDataSourceConfig> loadDataSourceConfig() {
        List<InfotamiaDataSourceConfig> dataSources = new ArrayList<>();
        Yaml yaml = new Yaml();

        try (InputStream inputStream = ConfigurationUtils.class.getClassLoader().getResourceAsStream("datasource-config.yaml")) {
            yaml.loadAll(inputStream)
                    .iterator().forEachRemaining(x -> {
                try {
                    InfotamiaDataSourceConfig ds = (InfotamiaDataSourceConfig) x;
                    dataSources.add(ds);
                } catch (Exception e) {
                    logger.error("Exception : ", e);
                }
            });
        } catch (IOException e) {
            logger.error("Exception : ", e);
        }
        return dataSources;
    }

    public static ConfigBean loadApplicationServerConfiguration() {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = ConfigurationUtils.class.getClassLoader().getResourceAsStream("config.yaml")) {
            return yaml.load(inputStream);
        } catch (IOException e) {
            logger.error("Exception : ", e);
        }
        return null;
    }
}
