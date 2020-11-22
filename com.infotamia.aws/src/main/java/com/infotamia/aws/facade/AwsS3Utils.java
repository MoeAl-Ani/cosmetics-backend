package com.infotamia.aws.facade;

import javax.enterprise.context.ApplicationScoped;

/**
 * @author Mohammed Al-Ani
 **/
@ApplicationScoped
public class AwsS3Utils {
    public String extractFilename(String url) {
        if (url == null || url.trim().isEmpty()) throw new RuntimeException("malformed aws url.");
        int index = url.lastIndexOf("/") + 1;
        return url.substring(index);
    }

    public String constructFilePath(String filename, String basePath) {
        //String basePath = "cosmetics/";
        return basePath + "/" + filename;
    }
}

