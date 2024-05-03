package com.dronetech.app.services;

import com.dronetech.app.dtos.DroneDto;
import com.dronetech.app.dtos.MedicationDto;
import com.dronetech.app.dtos.mappers.DroneDtoMapper;
import com.dronetech.app.entities.Drone;
import com.dronetech.app.entities.Medication;
import com.dronetech.app.entities.mappers.DroneEntityMapper;
import com.dronetech.app.entities.mappers.MedicationEntityMapper;
import com.dronetech.app.exceptions.DroneAlreadyRegisteredException;
import com.dronetech.app.exceptions.DroneMaxWeightExceededException;
import com.dronetech.app.exceptions.DroneNotFoundException;
import com.dronetech.app.exceptions.DroneStateAndBatteryMismatchException;
import com.dronetech.app.respositories.DroneRepository;
import com.dronetech.app.respositories.MedicationRepository;
import com.dronetech.app.services.api.FileUploadService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public DroneDto registerDrone(DroneDto droneDto) {
        ensureDroneIsNotRegistered(droneDto);
        ensureDroneStateMatchesBatteryCapacity(droneDto);
        Drone savedDrone = droneRepository.save(DroneEntityMapper.dtoToEntity(droneDto));
        return DroneDtoMapper.droneToDto(savedDrone);
    }

    public DroneDto retrieveDrone(String serialNo) {
        Drone drone = droneRepository.findBySerialNo(serialNo);
        ensureDroneNotNull(drone, serialNo);
        return DroneDtoMapper.droneToDto(drone);
    }

    @Transactional
    public DroneDto loadMedication(String serialNo, MedicationDto medicationDto) {
        // Perform Validations
        medicationDto.performCustomValidation();
        Drone drone = droneRepository.findBySerialNo(serialNo);
        ensureDroneNotNull(drone, serialNo);
        MedicationDto.SingleMedication[] singleMedications = medicationDto.getSingleMedications();
        ensureTotalWeightIsBelowMaxCapacity(drone, singleMedications);

        // Save Medication Files
        saveMedicationFiles(singleMedications);

        // Save Medications
        List<Medication> medications = Arrays.stream(singleMedications).map(MedicationEntityMapper::dtoToEntity).toList();
        medications.forEach(medication -> medication.setDrone(drone));
        List<Medication> savedMedications = medicationRepository.saveAll(medications);

        // Update Drone State
        drone.setState(determineState(drone, savedMedications));
        Drone savedDrone = droneRepository.save(drone);

        DroneDto droneDto = DroneDtoMapper.droneToDto(savedDrone);
        droneDto.setMedications(null); // intentionally not returning medications, has a separate endpoint
        return droneDto;
    }

    private void ensureDroneNotNull(Drone drone, String serialNo) {
        if (drone == null) {
            throw new DroneNotFoundException("Drone with Serial No '" + serialNo + "' not found");
        }
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

    private void ensureTotalWeightIsBelowMaxCapacity(Drone drone, MedicationDto.SingleMedication[] singleMedications) {
        double totalWeight = Arrays.stream(singleMedications).mapToDouble(MedicationDto.SingleMedication::getWeight).sum();

        if (drone.getMedications() != null) {
            totalWeight += drone.getMedications().stream().mapToDouble(Medication::getWeight).sum();
        }

        if (totalWeight > drone.getWeightLimit()) {
            throw new DroneMaxWeightExceededException("Total Weight of Medication cannot be above Drone's Max Capacity");
        }
    }

    private void saveMedicationFiles(MedicationDto.SingleMedication[] singleMedications) {
        for (MedicationDto.SingleMedication singleMedication : singleMedications) {
            String filePath = fileUploadService.saveFile(singleMedication.getImageFile(), singleMedication.getName());
            singleMedication.setImageUrl(filePath);
        }
    }

    private Drone.DroneState determineState(Drone drone, List<Medication> medications) {
        if (drone.getMedications() == null || drone.getMedications().isEmpty()) {
            return Drone.DroneState.IDLE;
        }

        Double totalWeight = drone.getMedications().stream().mapToDouble(Medication::getWeight).sum();
        totalWeight += medications.stream().mapToDouble(Medication::getWeight).sum();

        if (totalWeight.equals(drone.getWeightLimit())) {
            return Drone.DroneState.LOADED;
        }

        return Drone.DroneState.LOADING;
    }

}
