package com.dronetech.app.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DroneDto {

    @NotBlank
    @Size(
        max = 100,
        message = "Serial number must be less than 100 characters"
    )
    private String serialNo;

    @NotNull
    private DroneModelDto model;

    @Max(500)
    @NotNull
    private Double weightLimit;

    @Min(0)
    @Max(100)
    @NotNull
    private Integer batteryCapacity;

    @NotNull
    private DroneStateDto state;

    private List<MedicationDto.SingleMedication> medications;

    public enum DroneModelDto {
        LIGHTWEIGHT,
        MIDDLEWEIGHT,
        CRUISERWEIGHT,
        HEAVYWEIGHT
    }

    public enum DroneStateDto {
        IDLE,
        LOADING,
        LOADED,
        DELIVERING,
        DELIVERED,
        RETURNING
    }

}
