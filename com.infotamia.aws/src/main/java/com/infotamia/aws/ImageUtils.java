package com.infotamia.aws;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Image scale utility helper.
 * @author Mohammed Al-Ani
 **/
public class ImageUtils {

    public static byte[] resize(byte[] imageByte, int scaledWidth, int scaledHeight)
            throws IOException {
        // reads input image
        byte[] result = new byte[0];
        try (ByteArrayInputStream stream = new ByteArrayInputStream(imageByte)) {
            BufferedImage img = ImageIO.read(stream);
            if(scaledHeight == 0) {
                scaledHeight = (scaledWidth * img.getHeight())/ img.getWidth();
            }
            if(scaledWidth == 0) {
                scaledWidth = (scaledHeight * img.getWidth())/ img.getHeight();
            }
            Dimension scaledDimension = getScaledDimension(new Dimension(img.getWidth(), img.getHeight()), new Dimension(scaledWidth, scaledHeight));
            Image scaledImage = img.getScaledInstance((int)scaledDimension.getWidth(), (int)scaledDimension.getHeight(), Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage((int)scaledDimension.getWidth(), (int)scaledDimension.getHeight(), BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);

            try(ByteArrayOutputStream os = new ByteArrayOutputStream()) {
                ImageIO.write(imageBuff, "png", os);
                result = os.toByteArray();
            }
        }
        return result;
    }

    private static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {

        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }
}

