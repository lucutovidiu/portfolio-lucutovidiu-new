package com.lucutovidiu.mongo;

import com.lucutovidiu.models.User;
import com.lucutovidiu.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getByUserNameOrUserEmail(String user) {
        return userRepository.findByUserNameOrUserEmail(user, user);
    }
}
