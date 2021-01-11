package com.lucutovidiu.mongo;

import com.lucutovidiu.models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<User> getAllUsers();

    Optional<User> getByUserNameOrUserEmail(String user);
}
