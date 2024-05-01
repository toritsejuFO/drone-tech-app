package com.dronetech.app.controllers;

import com.dronetech.app.dtos.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping({"/", "/home"})
    public ResponseDto<String> home() {
        return ResponseDto.<String>builder()
            .status(HttpStatus.OK.value())
            .success(true)
            .message("Welcome to DroneTech")
            .build();
    }
}
