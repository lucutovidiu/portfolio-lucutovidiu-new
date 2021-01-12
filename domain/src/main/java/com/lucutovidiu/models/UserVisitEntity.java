package com.lucutovidiu.models;

import com.lucutovidiu.ip.Location;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "uservisits")
public class UserVisitEntity extends BaseEntity {
    private Location location;
}
