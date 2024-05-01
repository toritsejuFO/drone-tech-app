package com.dronetech.app.entities.mappers;

import com.dronetech.app.dtos.DroneDto;
import com.dronetech.app.entities.Drone;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EntityMapper {

    public static Drone dtoToEntity(DroneDto droneDto) {
        return Drone.builder()
            .serialNo(droneDto.getSerialNo())
            .model(modelDtoToEntity(droneDto.getModel()))
            .weightLimit(droneDto.getWeightLimit())
            .state(stateToEntity(droneDto.getState()))
            .batteryCapacity(droneDto.getBatteryCapacity())
            .build();
    }

    private static Drone.DroneModel modelDtoToEntity(DroneDto.DroneModelDto droneModelDto) {
        return Drone.DroneModel.valueOf(droneModelDto.name());
    }

    private static Drone.DroneState stateToEntity(DroneDto.DroneStateDto droneStateDto) {
        return Drone.DroneState.valueOf(droneStateDto.name());
    }
}
