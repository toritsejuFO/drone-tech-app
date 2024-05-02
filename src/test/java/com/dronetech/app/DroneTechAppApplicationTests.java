package com.dronetech.app;

import com.dronetech.app.dtos.DroneDto;
import com.dronetech.app.entities.mappers.EntityMapper;
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
    void registerDrone() throws Exception {
        DroneDto droneDto = podam.manufacturePojo(DroneDto.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/drone/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(droneDto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("success", is(true)));
    }

    @WithMockUser(value = "spring")
    @Test
    void retrieveDrone() throws Exception {
        DroneDto droneDto = podam.manufacturePojo(DroneDto.class);
        droneRepository.save(EntityMapper.dtoToEntity(droneDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/drone/" + droneDto.getSerialNo()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("success", is(true)));
    }


}
