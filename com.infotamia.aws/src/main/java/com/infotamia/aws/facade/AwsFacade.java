package com.infotamia.aws.facade;

import com.infotamia.aws.AwsS3ContentType;
import com.infotamia.aws.AwsS3Service;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author Mohammed Al-Ani
 **/
@ApplicationScoped
public class AwsFacade {
    public AwsFacade() {
        //
    }

    @Inject
    private AwsS3Utils awsS3Utils;
    @Inject
    private AwsS3Service awsS3Service;

    public String extractFilename(String url) {
        return awsS3Utils.extractFilename(url);
    }

    public String constructFilePath(String filename, String basePath) {
        return awsS3Utils.constructFilePath(filename, basePath);
    }

    public String addObjectToBucket(
            byte[] imageByte,
            String filepath,
            AwsS3ContentType awsS3ContentType,
            boolean isPublic) {
        return awsS3Service.addObjectToBucket(
                imageByte,filepath, awsS3ContentType, isPublic
        );
    }
    public void deleteObject(String key) {
        awsS3Service.deleteObject(key);
    }

    public String toString() {
        return super.toString();
    }
}
