package com.lucutovidiu.api.email;

import com.lucutovidiu.pojo.ContactRequest;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ContactRequestDto {

    @NotBlank
    private String senderName;
    @NotBlank
    @Email(message = "Email should be valid")
    private String senderEmail;
    @NotBlank
    private String senderMessage;

    public ContactRequest toContactRequest(){
        return ContactRequest.builder()
                .senderName(senderName)
                .senderEmail(senderEmail)
                .senderMessage(senderMessage)
                .build();
    }
}
