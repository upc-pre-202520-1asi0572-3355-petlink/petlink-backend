package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.DTO.HistorialClinicoRequest;
import com.petlink.petlink_backend.DTO.HistorialClinicoResponse;
import com.petlink.petlink_backend.entity.HistorialClinico;
import com.petlink.petlink_backend.service.HistorialClinicoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiales")
@CrossOrigin(origins = "*") // permite conexión desde Angular
public class HistorialClinicoController {
    private final HistorialClinicoService service;

    public HistorialClinicoController(HistorialClinicoService service) {
        this.service = service;
    }

    // Listar todos (ya devuelve solo el nombre de la mascota)
    @GetMapping
    public List<HistorialClinicoResponse> listar() {
        return service.listar();
    }

    // Crear con DTO request
    @PostMapping
    public HistorialClinico crear(@RequestBody HistorialClinicoRequest request) {
        return service.guardarDesdeRequest(request);
    }

    // Actualizar
    @PutMapping("/{id}")
    public HistorialClinico actualizar(@PathVariable Long id, @RequestBody HistorialClinicoRequest request) {
        HistorialClinico historial = service.guardarDesdeRequest(request);
        historial.setId(id);
        return historial;
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}