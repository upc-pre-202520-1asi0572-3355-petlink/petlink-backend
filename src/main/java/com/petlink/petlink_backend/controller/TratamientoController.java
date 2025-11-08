package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.DTO.TratamientoRequest;
import com.petlink.petlink_backend.DTO.TratamientoResponse;
import com.petlink.petlink_backend.service.TratamientoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tratamientos")
@CrossOrigin(origins = "*")
public class TratamientoController {

    private final TratamientoService service;

    public TratamientoController(TratamientoService service) {
        this.service = service;
    }

    @GetMapping
    public List<TratamientoResponse> listarTodos() {
        return service.listarTodos();
    }

    @PostMapping
    public TratamientoResponse crear(@Valid @RequestBody TratamientoRequest req) {
        return service.crear(req);
    }

    @PutMapping("/{id}")
    public TratamientoResponse actualizar(@PathVariable Long id,
            @Valid @RequestBody TratamientoRequest req) {
        return service.actualizar(id, req);
    }

    @PatchMapping("/{id}/estado")
    public TratamientoResponse cambiarEstado(@PathVariable Long id,
            @RequestParam String estado) {
        // estado: Activo | Finalizado | Suspendido
        return service.cambiarEstado(id, estado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}