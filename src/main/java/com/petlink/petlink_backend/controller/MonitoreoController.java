package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.DTO.MonitoreoRequest;
import com.petlink.petlink_backend.DTO.MonitoreoResponse;
import com.petlink.petlink_backend.DTO.PetMonitoringFullResponse;
import com.petlink.petlink_backend.service.MonitoreoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitoreo")
@CrossOrigin(origins = "*")
public class MonitoreoController {

    private final MonitoreoService service;

    public MonitoreoController(MonitoreoService service) {
        this.service = service;
    }

    @GetMapping
    public List<MonitoreoResponse> listar() {
        return service.listar();
    }

    @PostMapping
    public MonitoreoResponse crear(@RequestBody MonitoreoRequest req) {
        return service.crear(req);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @GetMapping("/pet/{id}/monitoring")
    public PetMonitoringFullResponse getMonitoring(@PathVariable Long id) {
        return service.getMonitoringView(id);
    }
}