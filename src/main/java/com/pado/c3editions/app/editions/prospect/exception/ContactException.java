package com.pado.c3editions.app.editions.prospect.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ContactException extends RuntimeException{
    private HttpStatus status;

    public ContactException(HttpStatus status) {
        this.status = status;
    }

    public ContactException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ContactException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public ContactException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

    public ContactException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus status) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.status = status;
    }
}
