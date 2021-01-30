package com.lucutovidiu.api.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.ALREADY_REPORTED)
public class DuplicateUserException extends RuntimeException {

    public DuplicateUserException(String msg) {
        super(msg);
    }
}
