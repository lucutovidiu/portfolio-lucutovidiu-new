package com.lucutovidiu.api.portfolios;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DateIncorrectException extends RuntimeException {

    public DateIncorrectException(String msg) {
        super(msg);
    }
}
