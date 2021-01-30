package com.lucutovidiu.mongo;

import com.lucutovidiu.models.UserEntity;
import com.lucutovidiu.users.dto.UserBasicDto;
import com.lucutovidiu.users.dto.UserDto;
import com.lucutovidiu.users.dto.UserRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    List<UserBasicDto> getAllUsers();

    Optional<UserEntity> getByUserNameOrUserEmail(String user);

    UserBasicDto saveUser(UserRequestDto userRequest);

    boolean deleteUser(String userId);

    UserDto getUserByUserId(String userId);

    boolean editUser(UserRequestDto userRequest);
}
