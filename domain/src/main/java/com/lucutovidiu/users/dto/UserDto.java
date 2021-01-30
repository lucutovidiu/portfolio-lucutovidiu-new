package com.lucutovidiu.users.dto;

import com.lucutovidiu.models.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {
    private String id;
    private String userName;
    private String userEmail;
    private List<UserRole> roles;
    private String gender;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;
}
