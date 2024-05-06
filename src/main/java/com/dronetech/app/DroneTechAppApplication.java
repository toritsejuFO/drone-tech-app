package com.dronetech.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DroneTechAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneTechAppApplication.class, args);
	}

}
