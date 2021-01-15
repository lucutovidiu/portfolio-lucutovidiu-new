package com.lucutovidiu.conversions;

import org.springframework.stereotype.Service;

@Service
public interface PhotoConversion {

    byte[] convertAndScaleImageToJpeg(byte[] imageBytes, int idealWidth);
}
