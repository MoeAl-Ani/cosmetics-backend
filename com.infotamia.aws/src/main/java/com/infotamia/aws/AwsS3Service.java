package com.infotamia.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.SetObjectAclRequest;
import com.infotamia.config.AwsS3Configuration;
import com.infotamia.config.ConfigEnum;
import com.infotamia.config.ConfigServiceQualifier;
import com.infotamia.logger.LoggerFactory;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * usage
 * String imageName = RandomStringUtils.randomAlphanumeric(16, 16);
 * String filePath = awsS3Utils.constructFilePath(imageName + ".png");
 * String imageUrl = awsS3Service.addObjectToBucket(
 *                     imageByte,
 *                     filePath,
 *                     AwsS3ContentType.IMAGE_PNG,
 *                     true);
 *
 * ProductImageEntity e = new ProductImageEntity();
 * e.setImageUrl(imageUrl);
 * productImageDao.insert(e);
 * @author Mohammed Al-Ani
 **/
@ApplicationScoped
public class AwsS3Service {

    @Inject
    @ConfigServiceQualifier(ConfigEnum.AWS_S3)
    private AwsS3Configuration awsS3Configuration;
    private final Logger logger = LoggerFactory.getLogger(AwsS3Service.class);

    private AmazonS3 amazonS3Client;


    public AwsS3Service() {
        //Required
    }

    @PostConstruct
    public void init() {
        AWSCredentials credentials = new BasicAWSCredentials(
                awsS3Configuration.getApiKey(),
                awsS3Configuration.getApiSecret()
        );
        amazonS3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(awsS3Configuration.getEndPoint(), awsS3Configuration.getRegion())
                )
                .build();
    }

    /**
     * Add new resource to the bucket
     *
     * @return the created url.
     */
    public String addObjectToBucket(
            byte[] imageByte,
            String filepath,
            AwsS3ContentType awsS3ContentType,
            boolean isPublic) {
        try {
            validateObjectPathKey(filepath);
            byte[] scaledImage = resizeImage(imageByte);
            try (InputStream in = new ByteArrayInputStream(scaledImage)) {
                ObjectMetadata metadata = new ObjectMetadata();
                metadata.setContentLength(scaledImage.length);
                metadata.setContentType(awsS3ContentType.getValue());

                amazonS3Client.putObject(awsS3Configuration.getBucketName(), filepath, in, metadata);
                if (isPublic) {
                    amazonS3Client.setObjectAcl(
                            new SetObjectAclRequest(awsS3Configuration.getBucketName(),
                                    filepath, CannedAccessControlList.PublicRead));
                }
                return amazonS3Client.getUrl(awsS3Configuration.getBucketName(), filepath).toString();
            }
        } catch (IOException e) {
            logger.error("Exception in AwsS3Service: ", e);
            return null;
        }

    }

    private byte[] resizeImage(byte[] imageByte) throws IOException {
        try {
            return ImageUtils.resize(imageByte, ImageConstants.SCALED_WIDTH, ImageConstants.SCALED_HEIGHT);
        } catch (IOException e) {
            logger.error("Exception while resizing image: ", e);
            throw e;
        }
    }

    /**
     * delete resource from awss3
     * @param key
     */
    public void deleteObject(String key) {
        validateObjectPathKey(key);
        amazonS3Client.deleteObject(awsS3Configuration.getBucketName(), key);
    }

    private void validateObjectPathKey(String key) {
        if (!key.matches("^cosmetics/\\d+/.+\\..+$")) {
            throw new IllegalArgumentException("key is not matching our rules");
        }
    }
}

