package com.lucutovidiu.login;

import com.lucutovidiu.api.login.LoginApi;
import com.lucutovidiu.api.login.LoginRequestDto;
import com.lucutovidiu.api.login.LoginResponseDto;
import com.lucutovidiu.jwt.JwtService;
import com.lucutovidiu.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LoginApiImpl implements LoginApi {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    public LoginResponseDto authenticate(LoginRequestDto loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.getUserName(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            LoginResponseDto responseDto = new LoginResponseDto();
            responseDto.setLoginSucceed(false);
            return responseDto;
        }
        return LoginResponseDto.getLoginResponseDto(
                jwtService.generateToken(customUserDetailsService.loadUserByUsername(loginRequest.getUserName())),
                true);
    }
}
