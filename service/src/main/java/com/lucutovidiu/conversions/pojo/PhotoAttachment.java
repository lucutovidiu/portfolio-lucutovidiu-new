package com.lucutovidiu.conversions.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PhotoAttachment {
    private byte[] content;
    private String fileExtension;
    private int desiredWidth;
}
