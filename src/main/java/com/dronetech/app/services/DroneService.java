package com.dronetech.app.services;

import com.dronetech.app.dtos.DroneDto;
import com.dronetech.app.dtos.MedicationDto;
import com.dronetech.app.dtos.mappers.DroneDtoMapper;
import com.dronetech.app.entities.Drone;
import com.dronetech.app.entities.Medication;
import com.dronetech.app.entities.mappers.DroneEntityMapper;
import com.dronetech.app.entities.mappers.MedicationEntityMapper;
import com.dronetech.app.exceptions.FileOperationException;
import com.dronetech.app.respositories.DroneRepository;
import com.dronetech.app.respositories.MedicationRepository;
import com.dronetech.app.services.api.FileUploadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DroneService {

    @Autowired
    private final DroneRepository droneRepository;
    @Autowired
    private final MedicationRepository medicationRepository;
    @Autowired
    private final FileUploadService fileUploadService;
    @Autowired
    private final DroneHelperService droneHelperService;

    public DroneDto registerDrone(DroneDto droneDto) {
        droneHelperService.ensureDroneIsNotRegistered(droneDto);
        droneHelperService.ensureDroneStateMatchesBatteryCapacity(droneDto);
        Drone savedDrone = droneRepository.save(DroneEntityMapper.dtoToEntity(droneDto));
        return DroneDtoMapper.droneToDto(savedDrone);
    }

    public DroneDto retrieveDrone(String serialNo) {
        Drone drone = droneRepository.findBySerialNo(serialNo);
        droneHelperService.ensureDroneNotNull(drone, serialNo);
        return DroneDtoMapper.droneToDto(drone);
    }

    public List<DroneDto> retrieveAllDrones() {
        List<Drone> drones = (List<Drone>) droneRepository.findAll();
        // Returns drones with their medications (w/o actual images)
        return drones.stream().map(DroneDtoMapper::droneToDto).toList();
    }

    @Transactional
    public DroneDto loadMedication(String serialNo, MedicationDto medicationDto) {
        // Perform Validations
        medicationDto.performCustomValidation();
        Drone drone = droneRepository.findBySerialNo(serialNo);
        droneHelperService.ensureDroneNotNull(drone, serialNo);
        droneHelperService.ensureDroneAvailableForLoading(drone);
        MedicationDto.SingleMedication[] singleMedications = medicationDto.getSingleMedications();
        droneHelperService.ensureTotalWeightIsBelowMaxCapacity(drone, singleMedications);

        // Save Medication Files
        droneHelperService.saveMedicationFiles(singleMedications);

        // Save Medications
        List<Medication> medications = Arrays.stream(singleMedications).map(MedicationEntityMapper::dtoToEntity).toList();
        medications.forEach(medication -> medication.setDrone(drone));
        List<Medication> savedMedications = medicationRepository.saveAll(medications);

        // Update Drone State
        drone.setState(droneHelperService.determineState(drone, savedMedications));
        Drone savedDrone = droneRepository.save(drone);

        DroneDto droneDto = DroneDtoMapper.droneToDto(savedDrone);
        droneDto.setMedications(null); // intentionally not returning medications, has a separate endpoint
        return droneDto;
    }

    public List<MedicationDto.SingleMedication> retrieveLoadedMedications(String serialNo, boolean withImage) {
        Drone drone = droneRepository.findBySerialNo(serialNo);
        droneHelperService.ensureDroneNotNull(drone, serialNo);
        DroneDto droneDto = DroneDtoMapper.droneToDto(drone);
        if (withImage) {
            droneDto.getMedications().forEach(medicationDto -> {
                try {
                    medicationDto.setImage(fileUploadService.getFile(medicationDto.getImageUrl()));
                } catch (IOException e) {
                    log.error("Error retrieving image file: {}", e.getMessage(), e);
                    throw new FileOperationException("Error retrieving image file");
                }
            });
        }
        return droneDto.getMedications();
    }

    public List<DroneDto> retrieveAvailableDrones() {
        List<Drone> drones = droneRepository.findByStateIn(List.of(Drone.DroneState.IDLE, Drone.DroneState.LOADING));
        List<DroneDto> droneDtos = drones.stream().map(DroneDtoMapper::droneToDto).toList();
        droneDtos.forEach(droneDto -> droneDto.setMedications(null));
        return droneDtos;
    }


    public Integer retrieveDroneBatteryLevel(String serialNo) {
        Drone drone = droneRepository.findBySerialNo(serialNo);
        droneHelperService.ensureDroneNotNull(drone, serialNo);
        return drone.getBatteryCapacity();
    }

}
