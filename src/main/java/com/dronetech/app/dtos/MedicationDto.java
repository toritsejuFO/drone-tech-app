package com.dronetech.app.dtos;

import com.dronetech.app.services.UtilityService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Slf4j
public class MedicationDto {

    private static final String NAME_REGEX = "^[a-zA-Z0-9_-]+$";
    private static final String CODE_REGEX = "^[A-Z0-9_]+$";

    @NotEmpty
    private MultipartFile[] image;

    @NotEmpty
    private String[] name;

    @NotEmpty
    private Double[] weight;

    @NotEmpty
    private String[] code;

    public void performCustomValidation() {
        if (name.length != image.length || name.length != weight.length || name.length != code.length) {
            throw new IllegalArgumentException("All fields must have the same length");
        }

        for (int i = 0; i < name.length; i++) {
            UtilityService.verifyRegex(name[i], NAME_REGEX, "Name can only contain alphanumeric characters, underscores and hyphens");
            UtilityService.verifyRegex(code[i], CODE_REGEX, "Code can only contain uppercase alphanumeric characters and underscores");
        }
    }

    @JsonIgnore
    public SingleMedication[] getSingleMedications() {
        SingleMedication[] singleMedications = new SingleMedication[name.length];

        for (int i = 0; i < name.length; i++) {
            singleMedications[i] = SingleMedication.builder()
                .imageFile(image[i])
                .name(name[i])
                .weight(weight[i])
                .code(code[i])
                .build();
        }
        return singleMedications;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @RequiredArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SingleMedication {
        private MultipartFile imageFile;
        private byte[] image;
        private String name;
        private Double weight;
        private String code;
        private String imageUrl;
    }

}
