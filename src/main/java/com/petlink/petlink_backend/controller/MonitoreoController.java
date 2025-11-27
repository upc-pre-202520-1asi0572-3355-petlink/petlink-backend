package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.DTO.MonitoreoResponse;
import com.petlink.petlink_backend.entity.Mascota;
import com.petlink.petlink_backend.repository.MascotaRepository;
import com.petlink.petlink_backend.repository.MonitoreoRepository;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitoreo")
@CrossOrigin("*")
public class MonitoreoController {

    private final MonitoreoRepository monitoreoRepo;
    private final MascotaRepository mascotaRepo;

    public MonitoreoController(MonitoreoRepository monitoreoRepo, MascotaRepository mascotaRepo) {
        this.monitoreoRepo = monitoreoRepo;
        this.mascotaRepo = mascotaRepo;
    }

    /**
     * Devuelve histórico de pulsos y actividad de una mascota
     */
    @GetMapping("/mascota/{idMascota}")
    public List<MonitoreoResponse> historial(@PathVariable Long idMascota) {

        Mascota mascota = mascotaRepo.findById(idMascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        return monitoreoRepo.findByMascota(mascota)
                .stream()
                .map(x -> new MonitoreoResponse(
                        x.getId(),
                        mascota.getId(),
                        mascota.getNombre(),
                        x.getRitmoCardiaco(),
                        x.getActividad(),
                        x.getUbicacion(),
                        x.getEstadoLED(),
                        x.getUltimaActualizacion()))
                .toList();
    }
}