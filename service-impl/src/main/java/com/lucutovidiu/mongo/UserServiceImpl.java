package com.lucutovidiu.mongo;

import com.lucutovidiu.models.UserEntity;
import com.lucutovidiu.mongo.exceptions.UserNotFoundException;
import com.lucutovidiu.repos.UserRepository;
import com.lucutovidiu.users.dto.UserBasicDto;
import com.lucutovidiu.users.dto.UserDto;
import com.lucutovidiu.users.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<UserBasicDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserEntity::toUserBasicDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserEntity> getByUserNameOrUserEmail(String user) {
        return userRepository.findByUserNameOrUserEmail(user, user);
    }

    @Override
    public UserBasicDto saveUser(UserRequestDto userRequest) {
        userRequest.setPassword(getEncoredPassword(userRequest.getPassword()));
        return userRepository.save(UserEntity.convert(userRequest)).toUserBasicDto();
    }

    @Override
    public boolean deleteUser(String userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!!!"));
        userRepository.delete(userEntity);
        return true;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        return userRepository.findById(userId)
                .map(UserEntity::toUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found!!!"));
    }

    @Override
    public boolean editUser(UserRequestDto userRequest) {
        userRequest.setPassword(getEncoredPassword(userRequest.getPassword()));
        userRepository.findById(userRequest.getId())
                .map(entity -> userRepository.save(entity.updateUserEntity(userRequest)))
                .orElseThrow(() -> new UserNotFoundException("User not found!!!"));
        return true;
    }

    private String getEncoredPassword(String unencryptedPassword) {
        if (!unencryptedPassword.trim().isEmpty()) {
            return bCryptPasswordEncoder.encode(unencryptedPassword);
        }
        return unencryptedPassword;
    }
}
