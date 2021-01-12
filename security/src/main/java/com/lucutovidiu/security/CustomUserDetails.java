package com.lucutovidiu.security;

import com.lucutovidiu.models.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomUserDetails implements UserDetails {

    private UserEntity userEntity;

    public CustomUserDetails() {
    }

    public CustomUserDetails(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userEntity.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getLabel()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUserEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return userEntity.getIsAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return userEntity.getIsAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return userEntity.getIsCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return userEntity.getIsEnabled();
    }
}
