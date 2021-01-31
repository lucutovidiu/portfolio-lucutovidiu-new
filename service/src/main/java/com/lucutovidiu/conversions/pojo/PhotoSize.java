package com.lucutovidiu.conversions.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PhotoSize {
    int width;
    int height;

    public static int getIdealThumbnailWidth() {
        return 800;
    }

    public static int getIdealMediumWidth() {
        return 800;
    }
}
