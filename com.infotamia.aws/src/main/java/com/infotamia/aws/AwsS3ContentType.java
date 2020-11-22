package com.infotamia.aws;

/**
 * @author Mohammed Al-Ani
 */
public enum AwsS3ContentType {

    IMAGE_JPEG("image/jpeg"), IMAGE_JPG("image/jpg"), IMAGE_PNG("image/png");
    String value;

    AwsS3ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

