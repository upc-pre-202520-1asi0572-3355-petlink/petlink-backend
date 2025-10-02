package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.DTO.DashboardSummary;
import com.petlink.petlink_backend.DTO.MonitoreoResponse;
import com.petlink.petlink_backend.service.DashboardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public DashboardSummary getSummary() {
        return service.getSummary();
    }

    @GetMapping("/monitoreo")
    public List<MonitoreoResponse> getMonitoreo() {
        return service.getMonitoreoActual();
    }
}