package com.lucutovidiu.household.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class WrongUserDeletingGroupException extends RuntimeException {

    public WrongUserDeletingGroupException(String msg) {
        super(msg);
    }
}
