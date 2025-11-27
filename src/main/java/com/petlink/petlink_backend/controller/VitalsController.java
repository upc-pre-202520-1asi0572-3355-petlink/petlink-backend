package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.DTO.VitalSignRequest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Vitals", description = "Manejo de signos vitales enviados desde IoT")
@RestController
@RequestMapping("/api/v1/vitals")
public class VitalsController {

    @PostMapping("/receive")
    public ResponseEntity<?> receiveVitals(@RequestBody VitalSignRequest payload) {
        System.out.println("==== VITALS RECEIVED FROM PETLINK DEVICE ====");
        System.out.println(payload);
        return ResponseEntity.ok().build();
    }
}