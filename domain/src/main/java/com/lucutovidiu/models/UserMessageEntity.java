package com.lucutovidiu.models;

import com.lucutovidiu.ip.Location;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "usermessages")
public class UserMessageEntity extends BaseEntity {
    private Location location;
    private String senderName;
    private String senderEmail;
    private String senderMessage;
}
