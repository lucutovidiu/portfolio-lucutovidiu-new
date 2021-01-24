package com.lucutovidiu.pages.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PortfolioException extends RuntimeException {

    public PortfolioException(String msg){
        super(msg);
    }
}
