package com.lucutovidiu.mongo;

import com.lucutovidiu.ip.Location;
import com.lucutovidiu.models.UserVisit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserVisitService {

    List<UserVisit> getUserVisits();

    UserVisit saveVisit(Location location);
}
