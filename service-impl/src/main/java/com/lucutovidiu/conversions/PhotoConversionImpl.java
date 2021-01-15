package com.lucutovidiu.conversions;

import com.lucutovidiu.conversions.pojo.PhotoAttachment;
import com.lucutovidiu.conversions.util.AttachmentUtil;
import com.lucutovidiu.conversions.util.ImageUtil;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class PhotoConversionImpl implements PhotoConversion {

    @Override
    public byte[] convertAndScaleImageToJpeg(byte[] imageBytes, int idealWidth) {
        try {
            String fileExtension = AttachmentUtil.getFileExtension(imageBytes);
            PhotoAttachment photoAttachment = new PhotoAttachment(imageBytes, fileExtension, idealWidth);
            if (AttachmentUtil.isPicture(fileExtension)) {
                return convertAndScaleImageToJpeg(photoAttachment);
            } else {
                throw new RuntimeException("Could not create Photo check type!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Could not create Photo check type!");
        }
    }

    byte[] convertAndScaleImageToJpeg(PhotoAttachment photoAttachment) {
        BufferedImage bufferedImage = AttachmentUtil.getBufferedImageFromAttachment(photoAttachment.getContent());
        BufferedImage rescaledImage = ImageUtil.scalePhoto(bufferedImage, ImageUtil.getIdealPhotoSize(bufferedImage.getWidth(), bufferedImage.getHeight(), photoAttachment.getDesiredWidth()));
        String fileExtension = photoAttachment.getFileExtension();
        if (AttachmentUtil.isNotJpgPicture(fileExtension)) {
            return convertImageToJpg(rescaledImage);
        } else {
            try {
                return ImageUtil.convert(rescaledImage, fileExtension);
            } catch (Exception e) {
                throw new RuntimeException("Could not create Photo check type!");
            }
        }
    }

    private byte[] convertImageToJpg(BufferedImage bufferedImage) {
        return AttachmentUtil.convertImageToJpg(bufferedImage);
    }
}
