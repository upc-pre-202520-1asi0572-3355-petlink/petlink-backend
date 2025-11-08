package com.petlink.petlink_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetlinkBackendApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PetlinkBackendApplication.class);
		// app.setAdditionalProfiles("local");
		app.run(args);
	}
}
