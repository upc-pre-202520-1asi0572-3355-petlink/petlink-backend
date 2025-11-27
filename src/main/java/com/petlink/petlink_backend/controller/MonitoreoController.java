package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.DTO.MonitoreoResponse;
import com.petlink.petlink_backend.DTO.VitalSignRequest;
import com.petlink.petlink_backend.entity.Collar;
import com.petlink.petlink_backend.entity.Mascota;
import com.petlink.petlink_backend.entity.Monitoreo;

import com.petlink.petlink_backend.repository.CollarRepository;
import com.petlink.petlink_backend.repository.MascotaRepository;
import com.petlink.petlink_backend.repository.MonitoreoRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/monitoreo")
@CrossOrigin("*")
public class MonitoreoController {

    private final MonitoreoRepository monitoreoRepo;
    private final MascotaRepository mascotaRepo;
    private final CollarRepository collarRepo;

    public MonitoreoController(MonitoreoRepository monitoreoRepo,
            MascotaRepository mascotaRepo,
            CollarRepository collarRepo) {

        this.monitoreoRepo = monitoreoRepo;
        this.mascotaRepo = mascotaRepo;
        this.collarRepo = collarRepo;
    }

    @PostMapping("/receive")
    public ResponseEntity<?> receive(@RequestBody VitalSignRequest request) {

        // Buscar collar
        Collar collar = collarRepo.findById(request.getCollarId())
                .orElseThrow(() -> new RuntimeException("Collar no encontrado"));

        Mascota mascota = collar.getMascotaAsignada();

        if (mascota == null || !mascota.isInternado()) {
            return ResponseEntity.badRequest().body("La mascota no está internada");
        }

        // Convertir timestamp del ESP32
        LocalDateTime fechaLeida;
        try {
            fechaLeida = LocalDateTime.parse(request.getTimestamp(),
                    DateTimeFormatter.ISO_DATE_TIME);
        } catch (Exception ex) {
            fechaLeida = LocalDateTime.now();
        }

        Monitoreo monitoreo = new Monitoreo();
        monitoreo.setMascota(mascota);
        monitoreo.setRitmoCardiaco(request.getHeartBpm());
        monitoreo.setEstadoLED(request.getState());
        monitoreo.setFecha(fechaLeida);
        monitoreo.setUbicacion("Clínica Veterinaria");
        monitoreo.setActividad(85);
        monitoreo.setUltimaActualizacion(LocalDateTime.now());

        monitoreoRepo.save(monitoreo);

        return ResponseEntity.ok("OK");
    }

    @GetMapping("/mascota/{id}")
    public List<MonitoreoResponse> obtenerByMascota(@PathVariable Long id) {

        return monitoreoRepo.findByMascotaId(id)
                .stream()
                .map(m -> new MonitoreoResponse(
                        m.getId(),
                        m.getMascota().getId(),
                        m.getMascota().getNombre(),
                        m.getRitmoCardiaco(),
                        m.getActividad(),
                        m.getUbicacion(),
                        m.getEstadoLED(),
                        m.getUltimaActualizacion()))
                .toList();
    }
}