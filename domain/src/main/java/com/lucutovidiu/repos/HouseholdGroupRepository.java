package com.lucutovidiu.repos;

import com.lucutovidiu.models.HouseholdGroupEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface HouseholdGroupRepository extends MongoRepository<HouseholdGroupEntity, String> {
    List<HouseholdGroupEntity> findAllByCreatedBy(String userName);

    Optional<HouseholdGroupEntity> findByIdAndCreatedBy(String id, String userName);
}
