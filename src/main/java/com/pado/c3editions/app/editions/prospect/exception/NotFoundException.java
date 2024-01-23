package com.pado.c3editions.app.editions.prospect.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {
    private HttpStatus status=HttpStatus.NO_CONTENT;
    public NotFoundException(String message) {
        super(message);
    }
}
