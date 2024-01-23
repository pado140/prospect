package com.pado.c3editions.app.editions.prospect.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Getter
public class FileException extends IOException {
    private HttpStatus status;

    public FileException(HttpStatus status) {
        this.status = status;
    }

    public FileException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public FileException(String message, Throwable cause, HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    public FileException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }

}
