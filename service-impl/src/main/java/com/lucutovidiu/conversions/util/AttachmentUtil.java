package com.lucutovidiu.conversions.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static java.util.Arrays.asList;


@Slf4j
@UtilityClass
public class AttachmentUtil {

    public final java.util.List<String> knownImageExtensions = asList("JPEG", "JPG", "PNG", "GIF", "TIFF");
    public final java.util.List<String> jpgExtensions = asList("JPEG", "JPG");
    public static String getMimeType(byte[] content) {
        return new Tika().detect(content);
    }

    public boolean isPicture(String mimeType) {
        return knownImageExtensions.stream().anyMatch(fileExtension -> fileExtension.equalsIgnoreCase(mimeType));
    }

    public boolean isNotJpgPicture(String mimeType) {
        return jpgExtensions.stream().noneMatch(fileExtension -> fileExtension.equalsIgnoreCase(mimeType));
    }

    public BufferedImage getBufferedImageFromAttachment(byte[] fileContent) {
        BufferedImage img;
        try {
            img = ImageIO.read(new ByteArrayInputStream(fileContent));
        } catch (IOException e) {
            throw new RuntimeException("Cannot read bytes from content!");
        }
        return img;
    }

    public static String getFileExtension(byte[] content) {

        try {
            TikaConfig config = TikaConfig.getDefaultConfig();

            MediaType mediaType = config.getMimeRepository().detect(new ByteArrayInputStream(content), new Metadata());
            MimeType mimeType = config.getMimeRepository().forName(mediaType.toString());
            return mimeType.getExtension().substring(1);
        } catch (IOException | MimeTypeException e) {
            log.error("Cound not get extension: ", e);
            throw new RuntimeException("Could not extract file exception!");
        }
    }

//    public static byte[] writeWatermarkToImageAndGetBytes(byte[] content) throws IOException {
//        String fileExtension = getFileExtension(content);
//        byte[] whiteBackgroundPNG = fillWhiteBackground(content, fileExtension);
//        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(whiteBackgroundPNG));
//
//        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
//        InputStream is = classloader.getResourceAsStream(WATERMARK_PATH);
//        BufferedImage watermarkImage = ImageIO.read(is);
//        BufferedImage scaledWatermark = ImageUtil.scalePhoto(watermarkImage, originalImage);
//
//        // applying watermark to the center of the image
//        int center = originalImage.getHeight() / 2 - scaledWatermark.getHeight() / 2;
//        Graphics g = originalImage.getGraphics();
//        g.drawImage(scaledWatermark, 0, center, null);
//
//        ByteArrayOutputStream imageWithWatermark = new ByteArrayOutputStream();
//        ImageIO.write(originalImage, fileExtension, imageWithWatermark);
//        return imageWithWatermark.toByteArray();
//    }

    private byte[] fillWhiteBackground(byte[] content, String fileExtension) throws IOException {
        if (!fileExtension.equals("png"))
            return content;
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(content));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage image = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        image.createGraphics().drawImage(originalImage, 0, 0, Color.WHITE, null);
        ImageIO.write(image, "jpg", outputStream);
        return outputStream.toByteArray();
    }

    private static byte[] fillWhiteBackground(byte[] content) throws IOException {
        return fillWhiteBackground(content, getFileExtension(content));
    }

    public byte[] convertImageToJpg(BufferedImage image){
        BufferedImage newBufferedImage = new BufferedImage(image.getWidth(),
                image.getHeight(), BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics().drawImage(image, 0, 0, Color.WHITE, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] imageInByte = null;
        try {
            ImageIO.write(newBufferedImage, "jpg", baos);
            baos.flush();
            imageInByte = baos.toByteArray();
            baos.close();
        } catch (IOException e) {
            log.error("Could not convert image to jpg ", e);
        }
        return imageInByte;
    }

}
