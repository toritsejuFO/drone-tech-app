package com.dronetech.app.controllers;

import com.dronetech.app.dtos.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Operation(summary = "Home", description = "Welcome to DroneTech")
    @GetMapping({"/", "/v1"})
    public ResponseDto<String> home() {
        return ResponseDto.<String>builder()
            .status(HttpStatus.OK.value())
            .success(true)
            .message("Welcome to DroneTech")
            .build();
    }
}
