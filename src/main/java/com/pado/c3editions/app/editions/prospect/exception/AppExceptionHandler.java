package com.pado.c3editions.app.editions.prospect.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ContactException.class)
    public ResponseEntity<Object> handleContactException(ContactException ex, HttpServletRequest request){
        return new ResponseEntity<Object>(AppError.builder().
                status(ex.getStatus()).
                timestamp(LocalDateTime.now()).
                message(ex.getMessage()).
                path(request.getContextPath()).
                build(),ex.getStatus());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException ex, HttpServletRequest request){
        return new ResponseEntity<Object>(AppError.builder().
                status(ex.getStatus()).
                timestamp(LocalDateTime.now()).
                message(ex.getMessage()).
                path(request.getContextPath()).
                build(),ex.getStatus());
    }

@ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> inputHandlerException(NotFoundException ex, HttpServletRequest request){
        return new ResponseEntity<Object>(AppError.builder().
                status(HttpStatus.NO_CONTENT).
                timestamp(LocalDateTime.now()).
                message(ex.getMessage()).
                path(request.getContextPath()).
                build(),HttpStatus.NO_CONTENT);
    }

}
