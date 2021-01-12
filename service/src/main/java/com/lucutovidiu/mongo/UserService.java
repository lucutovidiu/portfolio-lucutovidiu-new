package com.lucutovidiu.mongo;

import com.lucutovidiu.models.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<UserEntity> getAllUsers();

    Optional<UserEntity> getByUserNameOrUserEmail(String user);
}
