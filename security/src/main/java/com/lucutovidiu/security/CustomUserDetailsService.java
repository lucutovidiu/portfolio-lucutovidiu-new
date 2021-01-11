package com.lucutovidiu.security;

import com.lucutovidiu.models.User;
import com.lucutovidiu.mongo.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        Optional<User> repoUser = userService.getByUserNameOrUserEmail(user);
        if (repoUser.isPresent()) {
            return new CustomUserDetails(repoUser.get());
        } else throw new UsernameNotFoundException("UserName or Password is incorrect!");
    }
}
