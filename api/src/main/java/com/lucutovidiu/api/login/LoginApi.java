package com.lucutovidiu.api.login;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/login")
public interface LoginApi {

    @PostMapping
    LoginResponseDto authenticate(@RequestBody @Valid LoginRequestDto loginRequest);
}
