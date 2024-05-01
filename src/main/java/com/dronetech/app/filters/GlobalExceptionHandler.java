package com.dronetech.app.filters;

import com.dronetech.app.dtos.ErrorResponseDto;
import com.dronetech.app.exceptions.BaseException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorResponseDto handleException(Exception e) {
        return ErrorResponseDto.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .success(false)
            .message(e.getMessage())
            .build();
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponseDto handleException(NoResourceFoundException e) {
        return ErrorResponseDto.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .success(false)
            .message("Resource not found")
            .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorResponseDto handleException(MethodArgumentNotValidException e) {
        List<String> errors = new ArrayList<>();

        for (FieldError fe : e.getBindingResult().getFieldErrors()) {
            errors.add(fe.getField() + " - " + fe.getDefaultMessage());
        }

        return ErrorResponseDto.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .success(false)
            .message("Validation error")
            .errors(errors)
            .build();
    }

    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorResponseDto handleException(BaseException e) {
        return ErrorResponseDto.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .success(false)
            .message(e.getMessage())
            .errorCode(e.getErrorCode())
            .build();
    }

}
