package com.lucutovidiu.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Document(collection = "users")
public class User extends BaseEntity {
    @Indexed(unique = true)
    @NotBlank
    private String userName;
    @Indexed(unique = true)
    @NotBlank
    private String userEmail;
    @NotBlank
    private String password;
    @NotBlank
    private String role;
}
