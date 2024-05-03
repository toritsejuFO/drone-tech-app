package com.dronetech.app.controllers.v1;

import com.dronetech.app.dtos.DroneDto;
import com.dronetech.app.dtos.MedicationDto;
import com.dronetech.app.dtos.ResponseDto;
import com.dronetech.app.services.DroneService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("v1/drone")
@RequiredArgsConstructor
@RestController
@Slf4j
public class DispatchController {

    @Autowired
    private final DroneService droneService;

    @Operation(summary = "Register Drone", description = "Register a new drone")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
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
    public ResponseDto<DroneDto> retrieveDrone(@PathVariable String serialNo) {
        DroneDto drone = droneService.retrieveDrone(serialNo);
        return ResponseDto.<DroneDto>builder()
            .status(HttpStatus.OK.value())
            .success(true)
            .data(drone)
            .build();
    }

    @Operation(summary = "Load Medication", description = "Load medication into a drone by serial number")
    @PostMapping("/{serialNo}/medications")
    public ResponseDto<DroneDto> loadMedication(@ModelAttribute @Validated MedicationDto medicationDto, @PathVariable String serialNo) {
        DroneDto drone = droneService.loadMedication(serialNo, medicationDto);
        return ResponseDto.<DroneDto>builder()
            .status(HttpStatus.OK.value())
            .message("Medication loaded successfully")
            .success(true)
            .data(drone)
            .build();
    }

    @Operation(summary = "Get Loaded Medications", description = "Get all medications loaded into a drone by serial number")
    @GetMapping("/{serialNo}/medications")
    public ResponseDto<List<MedicationDto.SingleMedication>> retrieveLoadedMedications(@PathVariable String serialNo, @RequestParam(defaultValue = "false") boolean withImage) {
        List<MedicationDto.SingleMedication> medications = droneService.retrieveLoadedMedications(serialNo, withImage);
        return ResponseDto.<List<MedicationDto.SingleMedication>>builder()
            .status(HttpStatus.OK.value())
            .success(true)
            .data(medications)
            .build();
    }

    @Operation(summary = "Get Available Drones", description = "Get all available drones for loading")
    @GetMapping("/available")
    public ResponseDto<List<DroneDto>> retrieveAvailableDrones() {
        List<DroneDto> drones = droneService.retrieveAvailableDrones();
        return ResponseDto.<List<DroneDto>>builder()
            .status(HttpStatus.OK.value())
            .success(true)
            .data(drones)
            .build();
    }
}
