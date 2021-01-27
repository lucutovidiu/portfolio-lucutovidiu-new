package com.lucutovidiu.repos;

import com.lucutovidiu.models.HouseholdGroupEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HouseholdGroupRepository extends MongoRepository<HouseholdGroupEntity, String> {
}
