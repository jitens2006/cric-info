package com.poc.cric.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class PlayerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PlayerNotFoundException(String message) {
        super(message);
    }

    public PlayerNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
