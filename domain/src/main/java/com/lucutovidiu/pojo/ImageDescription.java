package com.lucutovidiu.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDescription {
    @NotBlank
    @Field(name = "image_src")
    private String imageSrc;
    @NotBlank
    @Field(name = "image_description")
    private String imageDescription;
}
