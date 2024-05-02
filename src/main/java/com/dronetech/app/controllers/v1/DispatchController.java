package com.dronetech.app.controllers.v1;

import com.dronetech.app.dtos.DroneDto;
import com.dronetech.app.dtos.ResponseDto;
import com.dronetech.app.services.DroneService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("v1/drone")
@RequiredArgsConstructor
@RestController
@Slf4j
public class DispatchController {

    @Autowired
    private final DroneService droneService;

    @Operation(summary = "Register Drone", description = "Register a new drone")
    @PostMapping("/register")
    public ResponseDto<DroneDto> registerDrone(@RequestBody @Validated DroneDto droneDto) {
        DroneDto createdDrone = droneService.registerDrone(droneDto);
        return ResponseDto.<DroneDto>builder()
            .status(HttpStatus.CREATED.value())
            .success(true)
            .data(createdDrone)
            .build();
    }

    @Operation(summary = "Retrieve Drone", description = "Retrieve a drone by serial number")
    @GetMapping("/{serialNo}")
    public ResponseDto<DroneDto> registerDrone(@PathVariable String serialNo) {
        DroneDto drone = droneService.retrieveDrone(serialNo);
        return ResponseDto.<DroneDto>builder()
            .status(HttpStatus.CREATED.value())
            .success(true)
            .data(drone)
            .build();
    }
}
