package com.dronetech.app;

import com.dronetech.app.dtos.DroneDto;
import com.dronetech.app.dtos.MedicationDto;
import com.dronetech.app.respositories.DroneRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DroneTechAppApplication.class)
@AutoConfigureMockMvc
@Slf4j
class DroneTechAppApplicationTests {

    PodamFactory podam = new PodamFactoryImpl();
    ObjectMapper mapper = new ObjectMapper();
    DroneDto globalDroneDto = podam.manufacturePojo(DroneDto.class);

    @Autowired
    MockMvc mockMvc;
    @Autowired
    DroneRepository droneRepository;

    @Test
    void contextLoads() {
    }

    @WithMockUser(value = "spring")
    @Test
    void home() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(true)));
    }

    @WithMockUser(value = "spring")
    @Test
    void test_registerDrone_successfully() throws Exception {
        DroneDto droneDto = podam.manufacturePojo(DroneDto.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/drone/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(droneDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("success", is(true)));
    }

    @WithMockUser(value = "spring")
    @Test
    void test_registerDrone_alreadyRegistered() throws Exception {
        DroneDto droneDto = podam.manufacturePojo(DroneDto.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/drone/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(droneDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("success", is(true)));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/drone/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(droneDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("success", is(false)))
            .andExpect(jsonPath("errorCode", containsString("DRONE_ALREADY_REGISTERED")));
    }

    @WithMockUser(value = "spring")
    @Test
    void test_registerDrone_stateAndBatteryCapacityMismatch() throws Exception {
        DroneDto droneDto = podam.manufacturePojo(DroneDto.class);
        droneDto.setBatteryCapacity(24);
        droneDto.setState(DroneDto.DroneStateDto.LOADING);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/drone/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(droneDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("success", is(false)))
            .andExpect(jsonPath("errorCode", is("DRONE_STATE_BATTERY_MISMATCH")));
    }

    @WithMockUser(value = "spring")
    @Test
    void test_retrieveDrone_successfully() throws Exception {
        String sn001 = "SN001";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/drone/" + sn001))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(true)));
    }

    @WithMockUser(value = "spring")
    @Test
    void test_retrieveDrone_nonExistingDrone() throws Exception {
        String nonExistingSn = "nonExistingSn";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/drone/" + nonExistingSn))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("success", is(false)))
            .andExpect(jsonPath("errorCode", is("DRONE_NOT_FOUND")));
    }

    @WithMockUser(value = "spring")
    @Test
    void test_retrieveAllDrones_successfully() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/drone/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(true)))
            .andExpect(jsonPath("$.data.length()", is(12)));
    }

    @WithMockUser(value = "spring")
    @Test
    void test_loadMedications_dtoValidation() throws Exception {
        MedicationDto medicationDto = podam.manufacturePojo(MedicationDto.class);
        String sn002 = "SN002";

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/drone/" + sn002 + "/medications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(medicationDto)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("success", is(false)))
            .andExpect(jsonPath("message", is("Validation error")));
    }

    @WithMockUser(value = "spring")
    @Test
    void test_retrieveLoadedMedications() throws Exception {
        String sn002 = "SN002";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/drone/" + sn002 + "/medications"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(true)))
            .andExpect(jsonPath("$.data.length()", is(2)));
    }

    @WithMockUser(value = "spring")
    @Test
    void test_retrieveAvailableDrones() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/drone/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(true)))
            .andExpect(jsonPath("$.data.length()", is(12)));
    }

    @WithMockUser(value = "spring")
    @Test
    void test_retrieveDroneBatteryLevel() throws Exception {
        String sn002 = "SN002";

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/drone/" + sn002 + "/battery"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(true)))
            .andExpect(jsonPath("data", is(25)));
    }

}
