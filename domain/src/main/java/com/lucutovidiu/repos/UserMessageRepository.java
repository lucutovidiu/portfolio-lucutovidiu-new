package com.lucutovidiu.repos;

import com.lucutovidiu.models.UserMessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMessageRepository extends MongoRepository<UserMessageEntity, String> {
}
