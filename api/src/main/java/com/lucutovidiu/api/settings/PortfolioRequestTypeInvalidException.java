package com.lucutovidiu.api.settings;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PortfolioRequestTypeInvalidException extends RuntimeException {

    public PortfolioRequestTypeInvalidException(String msg) {
        super(msg);
    }
}
