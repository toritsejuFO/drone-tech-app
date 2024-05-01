package com.dronetech.app.filters;

import com.dronetech.app.dtos.ResponseDto;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
@Order(-1)
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDto<String> handleException(Exception e) {
        return ResponseDto.<String>builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .success(false)
            .message(e.getMessage())
            .build();
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDto<String> handleException(NoResourceFoundException e) {
        return ResponseDto.<String>builder()
            .status(HttpStatus.NOT_FOUND.value())
            .success(false)
            .message("Resource not found")
            .build();
    }

}
