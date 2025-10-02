package com.petlink.petlink_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.petlink.petlink_backend")
public class PetlinkBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetlinkBackendApplication.class, args);
	}

}