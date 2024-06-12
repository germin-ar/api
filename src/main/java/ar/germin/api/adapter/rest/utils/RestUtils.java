package ar.germin.api.adapter.rest.utils;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

@UtilityClass
public class RestUtils {
    public static String fileUrlToBase64(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        BufferedImage img = ImageIO.read(url);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, getFileExtension(imageUrl), baos);
        byte[] bytes = baos.toByteArray();

        return Base64.getEncoder().encodeToString(bytes);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return "";
        }

        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == fileName.length() - 1) {
            return "";
        }

        return fileName.substring(lastDotIndex + 1);
    }
}
