package com.lucutovidiu.conversions.util;

import com.lucutovidiu.conversions.pojo.PhotoSize;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@UtilityClass
public class ImageUtil {

    public static BufferedImage convert(byte[] originalImage) throws IOException {
        return ImageIO.read(new ByteArrayInputStream(originalImage));
    }

    public static byte[] convert(BufferedImage originalImage, String imageFormatType) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, imageFormatType, baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }

    public PhotoSize getIdealPhotoSize(int originalWidth, int originalHeight, int idealWidth) {
        int width = originalWidth;
        int height = originalHeight;
        if (originalWidth > idealWidth) {
            int ratio = calculateAspectRatio(width, height);
            width = idealWidth;
            height = calculateNewHeight(width, ratio);
        }
        return new PhotoSize(width, height);
    }

    public int calculateAspectRatio(int oldWidth, int oldHeight) {
        return oldWidth / oldHeight;
    }

    public int calculateNewHeight(int newWidth, int aspectRatio) {
        return newWidth / aspectRatio;
    }

    public BufferedImage scalePhoto(BufferedImage originalImage, PhotoSize photoSize) {
        return Scalr.resize(originalImage, photoSize.getWidth(), photoSize.getHeight());
    }

    public static BufferedImage crop(BufferedImage image, int percent) {
        int cropLength = image.getWidth() / 10;
        return image.getSubimage(cropLength, cropLength, image.getWidth() - cropLength, image.getHeight() - cropLength);
    }

    public static BufferedImage convertToGrayscale(BufferedImage image) {
        //get image width and height
        int width = image.getWidth();
        int height = image.getHeight();

        //convert to grayscale
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int p = image.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                //calculate average
                int avg = (r + g + b) / 3;

                //replace RGB value with avg
                p = (a << 24) | (avg << 16) | (avg << 8) | avg;

                image.setRGB(x, y, p);
            }
        }

        return image;
    }

}
