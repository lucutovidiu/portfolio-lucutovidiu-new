package com.lucutovidiu.repos;

import com.lucutovidiu.models.UserVisit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMessagesRepository extends MongoRepository<UserVisit, String> {
}
