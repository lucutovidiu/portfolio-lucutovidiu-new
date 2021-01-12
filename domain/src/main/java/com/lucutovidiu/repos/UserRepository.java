package com.lucutovidiu.repos;

import com.lucutovidiu.models.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByUserNameOrUserEmail(String userName, String userEmail);
}
