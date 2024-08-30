package com.example.usermanagementservice.exception;


import com.example.usermanagementservice.models.dto.response.ErrorClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class SpringGlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorClass errorResponse = new ErrorClass(status.value(), errors);
        return new ResponseEntity<>(errorResponse, headers, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorClass> handleGlobalException(Exception ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        HttpStatus status;

        if (ex instanceof BadCredentialsException) {
            status = HttpStatus.UNAUTHORIZED;
            errors.add("Authentication Failure: Bad Credentials");
        } else if (ex instanceof AccessDeniedException) {
            status = HttpStatus.FORBIDDEN;
            errors.add("Authentication Failure: Access Denied");
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        errors.add(ex.getMessage());

        ErrorClass errorResponse = new ErrorClass(status.value(), errors);
        return new ResponseEntity<>(errorResponse, status);
    }
}