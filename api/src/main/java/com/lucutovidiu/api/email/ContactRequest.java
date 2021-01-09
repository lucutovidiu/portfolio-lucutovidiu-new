package com.lucutovidiu.api.email;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ContactRequest {

    @NotBlank
    private String senderName;
    @NotBlank
    @Email(message = "Email should be valid")
    private String senderEmail;
    @NotBlank
    private String senderMessage;
}
