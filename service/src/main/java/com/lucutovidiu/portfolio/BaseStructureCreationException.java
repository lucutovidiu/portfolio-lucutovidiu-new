package com.lucutovidiu.portfolio;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class BaseStructureCreationException extends RuntimeException {

    public BaseStructureCreationException(String msg) {
        super(msg);
    }
}
