package com.lucutovidiu.api.login;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
    private String jwtToken;
    private boolean loginSucceed;

    public static LoginResponseDto getLoginResponseDto(String jwtToken, boolean loginSucceed) {
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setJwtToken(jwtToken);
        responseDto.setLoginSucceed(loginSucceed);
        return responseDto;
    }
}
