package com.lucutovidiu.mongo;

import com.lucutovidiu.ip.Location;
import com.lucutovidiu.models.UserVisitEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserVisitService {

    List<UserVisitEntity> getUserVisits();

    UserVisitEntity saveVisit(Location location);
}
