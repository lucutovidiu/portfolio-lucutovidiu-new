package com.lucutovidiu.users.dto;

import com.lucutovidiu.models.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
public class UserRequestDto {
    private String id;
    @NotBlank
    private String userName;
    @NotBlank
    private String userEmail;
    private String password;
    @NotBlank
    private String gender;
    private List<UserRole> roles;
    private Boolean isAccountNonExpired;
    private Boolean isAccountNonLocked;
    private Boolean isCredentialsNonExpired;
    private Boolean isEnabled;
}
