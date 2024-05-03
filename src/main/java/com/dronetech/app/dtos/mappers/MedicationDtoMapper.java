package com.dronetech.app.dtos.mappers;

import com.dronetech.app.dtos.MedicationDto;
import com.dronetech.app.entities.Medication;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MedicationDtoMapper {

    public static MedicationDto.SingleMedication medicationToDto(Medication medication) {
        return MedicationDto.SingleMedication.builder()
            .imageUrl(medication.getImageUrl())
            .name(medication.getName())
            .weight(medication.getWeight())
            .code(medication.getCode())
            .build();
    }
}
