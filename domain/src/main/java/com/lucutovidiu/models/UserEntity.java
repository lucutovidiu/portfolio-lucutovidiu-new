package com.lucutovidiu.models;

import com.lucutovidiu.users.dto.UserBasicDto;
import com.lucutovidiu.users.dto.UserDto;
import com.lucutovidiu.users.dto.UserRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@Document(collection = "users")
public class UserEntity extends BaseEntity {
    @Indexed(unique = true)
    @NotBlank
    private String userName;
    @Indexed(unique = true)
    @NotBlank
    private String userEmail;
    @NotBlank
    private String password;
    @NotBlank
    private List<UserRole> roles;
    private String gender;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;

    public static UserEntity convert(UserRequestDto userRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userRequest.getUserName());
        userEntity.setUserEmail(userRequest.getUserEmail());
        userEntity.setPassword(userRequest.getPassword());
        userEntity.setRoles(userRequest.getRoles());
        userEntity.setGender(userRequest.getGender());
        userEntity.setIsAccountNonExpired(userRequest.getIsAccountNonExpired());
        userEntity.setIsAccountNonLocked(userRequest.getIsAccountNonLocked());
        userEntity.setIsCredentialsNonExpired(userRequest.getIsCredentialsNonExpired());
        userEntity.setIsEnabled(userRequest.getIsEnabled());
        return userEntity;
    }

    public UserEntity updateUserEntity(UserRequestDto userRequest) {
        if(!userRequest.getPassword().isEmpty()){
            password = userRequest.getPassword();
        }
        userName = userRequest.getUserName();
        userEmail = userRequest.getUserEmail();
        roles = userRequest.getRoles();
        gender = userRequest.getGender();
        isAccountNonExpired = userRequest.getIsAccountNonExpired();
        isAccountNonLocked = userRequest.getIsAccountNonLocked();
        isCredentialsNonExpired = userRequest.getIsCredentialsNonExpired();
        isEnabled = userRequest.getIsEnabled();
        return this;
    }

    public static UserDto toUserDto(UserEntity entity) {
        return UserDto.builder()
                .id(entity.getId())
                .userName(entity.getUserName())
                .userEmail(entity.getUserEmail())
                .roles(entity.getRoles())
                .gender(entity.getGender())
                .isAccountNonExpired(entity.getIsAccountNonExpired())
                .isAccountNonLocked(entity.getIsAccountNonLocked())
                .isCredentialsNonExpired(entity.getIsCredentialsNonExpired())
                .isEnabled(entity.getIsEnabled())
                .build();
    }

    public UserBasicDto toUserBasicDto() {
        UserBasicDto dto = new UserBasicDto();
        dto.setId(getId());
        dto.setUserName(userName);
        dto.setUserEmail(userEmail);
        return dto;
    }
}
