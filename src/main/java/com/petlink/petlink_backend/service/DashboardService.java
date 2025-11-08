package com.petlink.petlink_backend.service;

import com.petlink.petlink_backend.DTO.DashboardSummary;
import com.petlink.petlink_backend.DTO.MonitoreoResponse;
import com.petlink.petlink_backend.entity.Monitoreo;
import com.petlink.petlink_backend.repository.MonitoreoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final MonitoreoRepository repo;

    public DashboardService(MonitoreoRepository repo) {
        this.repo = repo;
    }

    public DashboardSummary getSummary() {
        List<Monitoreo> monitoreos = repo.findAll();

        long total = monitoreos.size();
        long criticos = monitoreos.stream().filter(m -> "Rojo".equalsIgnoreCase(m.getEstadoLED())).count();
        long estables = monitoreos.stream().filter(m -> "Verde".equalsIgnoreCase(m.getEstadoLED())).count();
        long alertas = monitoreos.stream().filter(m -> "Amarillo".equalsIgnoreCase(m.getEstadoLED())).count();

        return new DashboardSummary(total, criticos, estables, alertas);
    }

    public List<MonitoreoResponse> getMonitoreoActual() {
        return repo.findAll().stream().map(m -> new MonitoreoResponse(
                m.getId(),
                m.getMascota().getId(),
                m.getMascota().getNombre(),
                m.getRitmoCardiaco(),
                m.getActividad(),
                m.getUbicacion(),
                m.getEstadoLED(),
                m.getUltimaActualizacion())).collect(Collectors.toList());
    }
}