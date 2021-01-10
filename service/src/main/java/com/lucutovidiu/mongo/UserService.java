package com.lucutovidiu.mongo;

import com.lucutovidiu.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    List<User> getAllUsers();
}
