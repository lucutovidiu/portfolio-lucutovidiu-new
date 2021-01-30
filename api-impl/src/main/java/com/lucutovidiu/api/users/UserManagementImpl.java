package com.lucutovidiu.api.users;

import com.lucutovidiu.mongo.UserService;
import com.lucutovidiu.users.dto.UserBasicDto;
import com.lucutovidiu.users.dto.UserDto;
import com.lucutovidiu.users.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserManagementImpl implements UserManagement {

    private final UserService userService;

    @Override
    public UserBasicDto postUser(UserRequestDto userRequest) {
        try {
            return userService.saveUser(userRequest);
        } catch (DuplicateKeyException exception) {
            throw new DuplicateUserException("User Already Reported");
        }
    }

    @Override
    public boolean editUser(UserRequestDto userRequest) {
        return userService.editUser(userRequest);
    }

    @Override
    public List<UserBasicDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Override
    public boolean deleteUser(String userId) {
        return userService.deleteUser(userId);
    }

    @Override
    public UserDto getByUserId(String userId) {
        return userService.getUserByUserId(userId);
    }
}
