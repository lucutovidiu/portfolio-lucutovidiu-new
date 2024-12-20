package com.lucutovidiu.mongo;

import com.lucutovidiu.models.UserEntity;
import com.lucutovidiu.mongo.exceptions.UserNotFoundException;
import com.lucutovidiu.repos.UserRepository;
import com.lucutovidiu.users.dto.UserBasicDto;
import com.lucutovidiu.users.dto.UserDto;
import com.lucutovidiu.users.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lucutovidiu.cache.CacheNames.*;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Cacheable(value = GET_ALL_USERS)
    public List<UserBasicDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserEntity::toUserBasicDto)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = USER_GET_BY_ID, key = "#user")
    public Optional<UserEntity> getByUserNameOrUserEmail(String user) {
        return userRepository.findByUserNameOrUserEmail(user, user);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = USER_GET_BY_ID, allEntries = true),
                    @CacheEvict(value = USER_GET_BY_ID_DTO, allEntries = true),
                    @CacheEvict(value = GET_ALL_USERS, allEntries = true)
            }
    )
    public UserBasicDto saveUser(UserRequestDto userRequest) {
        userRequest.setPassword(getEncoredPassword(userRequest.getPassword()));
        return userRepository.save(UserEntity.convert(userRequest)).toUserBasicDto();
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = USER_GET_BY_ID, allEntries = true),
                    @CacheEvict(value = USER_GET_BY_ID_DTO, allEntries = true),
                    @CacheEvict(value = GET_ALL_USERS, allEntries = true)
            }
    )
    public boolean deleteUser(String userId) {
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!!!"));
        userRepository.delete(userEntity);
        return true;
    }

    @Override
    @Cacheable(value = USER_GET_BY_ID_DTO, key = "#userId")
    public UserDto getUserByUserId(String userId) {
        return userRepository.findById(userId)
                .map(UserEntity::toUserDto)
                .orElseThrow(() -> new UserNotFoundException("User not found!!!"));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = USER_GET_BY_ID, allEntries = true),
                    @CacheEvict(value = USER_GET_BY_ID_DTO, allEntries = true)
            }
    )
    public boolean editUser(UserRequestDto userRequest) {
        userRequest.setPassword(getEncoredPassword(userRequest.getPassword()));
        userRepository.findById(userRequest.getId())
                .map(entity -> userRepository.save(entity.updateUserEntity(userRequest)))
                .orElseThrow(() -> new UserNotFoundException("User not found!!!"));
        return true;
    }

    @Override
    @Transactional
    public boolean changePassword(String userEmail, String newPassword) {
        return getByUserNameOrUserEmail(userEmail)
                .map(userEntity -> {
                    log.info("Changing password for user email: {}", userEmail);
                    userEntity.setPassword(getEncoredPassword(newPassword));
                    userRepository.save(userEntity);
                    return true;
                }).orElse(false);
    }

    private String getEncoredPassword(String unencryptedPassword) {
        if (!unencryptedPassword.trim().isEmpty()) {
            return bCryptPasswordEncoder.encode(unencryptedPassword);
        }
        return unencryptedPassword;
    }
}
