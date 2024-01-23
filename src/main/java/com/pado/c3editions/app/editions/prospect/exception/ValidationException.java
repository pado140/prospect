package com.pado.c3editions.app.editions.prospect.exception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.logging.Level;
import java.util.logging.Logger;

@Getter
@Slf4j
public class ValidationException extends MethodArgumentNotValidException {
    /**
     * Constructor for {@link MethodArgumentNotValidException}.
     *
     * @param parameter     the parameter that failed validation
     * @param bindingResult the results of the validation
     */

    private HttpStatus status;


    public ValidationException(MethodParameter parameter, BindingResult bindingResult) {
        super(parameter, bindingResult);
//        log(Level.ALL,"Validation fails",parameter.getParameterName());
    }

    public ValidationException(MethodParameter parameter, BindingResult bindingResult, HttpStatus status) {
        super(parameter, bindingResult);

        this.status = status;
    }
}
