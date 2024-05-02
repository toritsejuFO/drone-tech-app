package com.dronetech.app.services;

import com.dronetech.app.dtos.DroneDto;
import com.dronetech.app.dtos.mappers.DtoMapper;
import com.dronetech.app.entities.Drone;
import com.dronetech.app.entities.mappers.EntityMapper;
import com.dronetech.app.exceptions.DroneAlreadyRegisteredException;
import com.dronetech.app.exceptions.DroneNotFoundException;
import com.dronetech.app.exceptions.DroneStateAndBatteryMismatchException;
import com.dronetech.app.respositories.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DroneService {

    @Autowired
    private final DroneRepository droneRepository;

    public DroneDto registerDrone(DroneDto droneDto) {
        ensureDroneIsNotRegistered(droneDto);
        ensureDroneStateMatchesBatteryCapacity(droneDto);
        Drone savedDrone = droneRepository.save(EntityMapper.dtoToEntity(droneDto));
        return DtoMapper.droneToDto(savedDrone);
    }

    public DroneDto retrieveDrone(String serialNo) {
        Drone drone = droneRepository.findBySerialNo(serialNo);
        if (drone == null) {
            throw new DroneNotFoundException("Drone with Serial No '" + serialNo + "' not found");
        }
        return DtoMapper.droneToDto(drone);
    }

    private void ensureDroneIsNotRegistered(DroneDto droneDto) {
        if (droneRepository.existsBySerialNo(droneDto.getSerialNo())) {
            throw new DroneAlreadyRegisteredException("Drone with Serial No '" + droneDto.getSerialNo() + "' is already registered");
        }
    }

    private void ensureDroneStateMatchesBatteryCapacity(DroneDto droneDto) {
        if (DroneDto.DroneStateDto.LOADING.equals(droneDto.getState()) && droneDto.getBatteryCapacity() < 25) {
            throw new DroneStateAndBatteryMismatchException("Battery Capacity cannot be below 25 when Drone State is LOADING");
        }
    }
}
