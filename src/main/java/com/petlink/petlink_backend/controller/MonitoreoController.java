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

        Mascota mascota = mascotaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con ID: " + id));

        if (!mascota.isInternado()) {
            throw new RuntimeException("La mascota no se encuentra internada actualmente.");
        }

        return monitoreoRepo.findByMascotaId(id)
                .stream()
                .sorted((m1, m2) -> m2.getFecha().compareTo(m1.getFecha())) // Ordenar por fecha descendente (más reciente primero)
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

    @GetMapping("/all")
    public List<MonitoreoResponse> obtenerTodos() {
        return monitoreoRepo.findAll()
                .stream()
                .filter(m -> m.getMascota().isInternado()) // Solo mascotas actualmente internadas
                .sorted((m1, m2) -> m2.getFecha().compareTo(m1.getFecha())) // Ordenar por fecha descendente
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