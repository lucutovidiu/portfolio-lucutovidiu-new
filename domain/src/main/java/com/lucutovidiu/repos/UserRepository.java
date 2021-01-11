package com.lucutovidiu.repos;

import com.lucutovidiu.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUserNameOrUserEmail(String userName, String userEmail);
}
