package com.lucutovidiu.mongo;

import com.lucutovidiu.ip.Location;
import com.lucutovidiu.models.UserVisitEntity;
import com.lucutovidiu.repos.UserVisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserVisitServiceImpl implements UserVisitService {

    private final UserVisitRepository userVisitRepository;

    @Override
    public List<UserVisitEntity> getUserVisits() {
        return userVisitRepository.findAll();
    }

    @Override
    public UserVisitEntity saveVisit(Location location) {
        UserVisitEntity userVisitEntity = new UserVisitEntity();
        userVisitEntity.setLocation(location);
        return userVisitRepository.save(userVisitEntity);
    }
}
