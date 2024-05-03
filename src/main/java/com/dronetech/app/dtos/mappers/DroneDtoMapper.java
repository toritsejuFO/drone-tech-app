package com.dronetech.app.dtos.mappers;

import com.dronetech.app.dtos.DroneDto;
import com.dronetech.app.entities.Drone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DroneDtoMapper {

    public static DroneDto droneToDto(Drone drone) {
        return DroneDto.builder()
            .serialNo(drone.getSerialNo())
            .model(modelToDto(drone.getModel()))
            .weightLimit(drone.getWeightLimit())
            .state(stateToDto(drone.getState()))
            .batteryCapacity(drone.getBatteryCapacity())
            .medications(drone.getMedications() != null ? drone.getMedications().stream().map(MedicationDtoMapper::medicationToDto).toList() : null)
            .build();
    }

    private static DroneDto.DroneModelDto modelToDto(Drone.DroneModel droneModel) {
        return DroneDto.DroneModelDto.valueOf(droneModel.name());
    }

    private static DroneDto.DroneStateDto stateToDto(Drone.DroneState droneState) {
        return DroneDto.DroneStateDto.valueOf(droneState.name());
    }
}
