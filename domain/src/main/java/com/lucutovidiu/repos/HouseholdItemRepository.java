package com.lucutovidiu.repos;

import com.lucutovidiu.models.HouseholdItemEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HouseholdItemRepository extends MongoRepository<HouseholdItemEntity, String> {
}
