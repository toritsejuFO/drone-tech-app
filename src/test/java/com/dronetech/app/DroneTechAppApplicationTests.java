package com.dronetech.app;

import com.dronetech.app.dtos.DroneDto;
import com.dronetech.app.entities.mappers.DroneEntityMapper;
import com.dronetech.app.respositories.DroneRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DroneTechAppApplication.class)
@AutoConfigureMockMvc
class DroneTechAppApplicationTests {

    PodamFactory podam = new PodamFactoryImpl();
    ObjectMapper mapper = new ObjectMapper();
    DroneDto droneDto = podam.manufacturePojo(DroneDto.class);

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
            .andExpect(jsonPath("message", containsString("is already registered")));
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
            .andExpect(jsonPath("message", is("Battery Capacity cannot be below 25 when Drone State is LOADING")));
    }

    @WithMockUser(value = "spring")
    @Test
    void retrieveDrone() throws Exception {
        DroneDto droneDto = podam.manufacturePojo(DroneDto.class);
        droneRepository.save(DroneEntityMapper.dtoToEntity(droneDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/drone/" + droneDto.getSerialNo()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(true)));
    }


}
