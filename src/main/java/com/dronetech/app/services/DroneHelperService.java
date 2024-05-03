package com.dronetech.app.services;

import com.dronetech.app.dtos.DroneDto;
import com.dronetech.app.dtos.MedicationDto;
import com.dronetech.app.entities.Drone;
import com.dronetech.app.entities.Medication;
import com.dronetech.app.exceptions.DroneAlreadyRegisteredException;
import com.dronetech.app.exceptions.DroneMaxWeightExceededException;
import com.dronetech.app.exceptions.DroneNotFoundException;
import com.dronetech.app.exceptions.DroneStateAndBatteryMismatchException;
import com.dronetech.app.exceptions.DroneUnavailableForLoadingException;
import com.dronetech.app.respositories.DroneRepository;
import com.dronetech.app.services.api.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneHelperService {

    @Autowired
    private final DroneRepository droneRepository;

    @Autowired
    private final FileUploadService fileUploadService;

    public void ensureDroneNotNull(Drone drone, String serialNo) {
        if (drone == null) {
            throw new DroneNotFoundException("Drone with Serial No '" + serialNo + "' not found");
        }
    }

    public void ensureDroneIsNotRegistered(DroneDto droneDto) {
        if (droneRepository.existsBySerialNo(droneDto.getSerialNo())) {
            throw new DroneAlreadyRegisteredException("Drone with Serial No '" + droneDto.getSerialNo() + "' is already registered");
        }
    }

    public void ensureDroneStateMatchesBatteryCapacity(DroneDto droneDto) {
        if (DroneDto.DroneStateDto.LOADING.equals(droneDto.getState()) && droneDto.getBatteryCapacity() < 25) {
            throw new DroneStateAndBatteryMismatchException("Battery Capacity cannot be below 25 when Drone State is LOADING");
        }
    }

    public void ensureDroneAvailableForLoading(Drone drone) {
        if (!List.of(Drone.DroneState.LOADING, Drone.DroneState.LOADING).contains(drone.getState())) {
            throw new DroneUnavailableForLoadingException("Drone is not available for loading Medication");
        }
    }

    public void ensureTotalWeightIsBelowMaxCapacity(Drone drone, MedicationDto.SingleMedication[] singleMedications) {
        double totalWeight = Arrays.stream(singleMedications).mapToDouble(MedicationDto.SingleMedication::getWeight).sum();

        if (drone.getMedications() != null) {
            totalWeight += drone.getMedications().stream().mapToDouble(Medication::getWeight).sum();
        }

        if (totalWeight > drone.getWeightLimit()) {
            throw new DroneMaxWeightExceededException("Total Weight of Medication cannot be above Drone's Max Capacity");
        }
    }

    public void saveMedicationFiles(MedicationDto.SingleMedication[] singleMedications) {
        for (MedicationDto.SingleMedication singleMedication : singleMedications) {
            String filePath = fileUploadService.saveFile(singleMedication.getImageFile(), singleMedication.getName());
            singleMedication.setImageUrl(filePath);
        }
    }

    public Drone.DroneState determineState(Drone drone, List<Medication> medications) {
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
