package com.meli.challenge.mutants.exception.handler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<Object> handleAccessDeniedException(
            MethodArgumentNotValidException ex, WebRequest request) {

        String errors = Optional.ofNullable(ex.getFieldError())
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(null);

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
