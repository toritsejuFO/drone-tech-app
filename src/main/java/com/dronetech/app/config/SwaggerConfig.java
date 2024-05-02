package com.dronetech.app.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
            .info(new Info().title("Drone Tech App")
                .description("Swagger docs for Drone Tech App")
                .version("v1"));
    }

}
