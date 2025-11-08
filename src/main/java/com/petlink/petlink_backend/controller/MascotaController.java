package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.entity.Mascota;
import com.petlink.petlink_backend.service.MascotaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
public class MascotaController {
    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @GetMapping
    public List<Mascota> obtenerMascotas() {
        return mascotaService.listarMascotas();
    }

    @PostMapping
    public Mascota registrarMascota(@Valid @RequestBody Mascota mascota) {
        return mascotaService.guardarMascota(mascota);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mascota> actualizarMascota(@PathVariable Long id,
            @Valid @RequestBody Mascota mascotaDetalles) {
        return mascotaService.actualizarMascota(id, mascotaDetalles)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMascota(@PathVariable Long id) {
        if (mascotaService.eliminarMascota(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}