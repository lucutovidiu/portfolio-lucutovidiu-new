package com.lucutovidiu.api.login;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
}
