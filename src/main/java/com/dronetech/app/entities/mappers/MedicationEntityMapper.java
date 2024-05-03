package com.dronetech.app.entities.mappers;

import com.dronetech.app.dtos.MedicationDto;
import com.dronetech.app.entities.Medication;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MedicationEntityMapper {

    public static Medication dtoToEntity(MedicationDto.SingleMedication singleMedication) {
        return Medication.builder()
            .imageUrl(singleMedication.getImageUrl())
            .name(singleMedication.getName())
            .weight(singleMedication.getWeight())
            .code(singleMedication.getCode())
            .build();
    }
}
