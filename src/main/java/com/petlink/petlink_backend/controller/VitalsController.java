package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.DTO.VitalSignRequest;
import com.petlink.petlink_backend.entity.Collar;
import com.petlink.petlink_backend.entity.Mascota;
import com.petlink.petlink_backend.entity.Monitoreo;
import com.petlink.petlink_backend.repository.CollarRepository;
import com.petlink.petlink_backend.repository.MonitoreoRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/vitals")
@CrossOrigin("*")
public class VitalsController {

    private final CollarRepository collarRepo;
    private final MonitoreoRepository monitoreoRepo;

    public VitalsController(CollarRepository collarRepo, MonitoreoRepository monitoreoRepo) {
        this.collarRepo = collarRepo;
        this.monitoreoRepo = monitoreoRepo;
    }

    /**
     * Recibe datos desde Wokwi (ESP32)
     */
    @PostMapping("/receive")
    public ResponseEntity<?> receive(@RequestBody VitalSignRequest payload) {

        Collar collar = collarRepo.findById(payload.collarId)
                .orElseThrow(() -> new RuntimeException("Collar no encontrado"));

        Mascota mascota = collar.getMascotaAsignada();

        if (mascota == null)
            return ResponseEntity.badRequest().body("El collar no está asignado a ninguna mascota");

        Monitoreo registro = new Monitoreo();
        registro.setMascota(mascota);
        registro.setRitmoCardiaco(payload.heartBpm);
        registro.setActividad(100);
        registro.setUbicacion("Clinica - Sala 1");
        registro.setEstadoLED(payload.state);
        registro.setFecha(payload.timestamp);
        registro.setUltimaActualizacion(LocalDateTime.now());

        monitoreoRepo.save(registro);

        return ResponseEntity.ok("OK");
    }
}