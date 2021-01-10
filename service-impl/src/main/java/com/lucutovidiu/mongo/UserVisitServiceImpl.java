package com.lucutovidiu.mongo;

import com.lucutovidiu.ip.Location;
import com.lucutovidiu.models.UserVisit;
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
    public List<UserVisit> getUserVisits() {
        return userVisitRepository.findAll();
    }

    @Override
    public UserVisit saveVisit(Location location) {
        UserVisit userVisit = new UserVisit();
        userVisit.setLocation(location);
        return userVisitRepository.save(userVisit);
    }
}
