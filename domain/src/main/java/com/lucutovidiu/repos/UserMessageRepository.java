package com.lucutovidiu.repos;

import com.lucutovidiu.models.UserMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserMessageRepository extends MongoRepository<UserMessage, String> {
}
