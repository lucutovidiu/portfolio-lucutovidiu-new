package com.lucutovidiu.pojo;

import com.lucutovidiu.ip.Location;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContactRequest {
    private Location location;
    private String senderName;
    private String senderEmail;
    private String senderMessage;
}
