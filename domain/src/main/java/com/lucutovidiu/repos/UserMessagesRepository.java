package com.lucutovidiu.repos;

import com.lucutovidiu.models.UserVisitEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMessagesRepository extends MongoRepository<UserVisitEntity, String> {
}
