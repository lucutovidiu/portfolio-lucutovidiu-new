package com.lucutovidiu.repos;

import com.lucutovidiu.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
