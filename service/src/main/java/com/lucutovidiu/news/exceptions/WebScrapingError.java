package com.lucutovidiu.news.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WebScrapingError extends RuntimeException {
    public WebScrapingError(String msg) {
        super(msg);
    }
}
