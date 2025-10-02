package com.petlink.petlink_backend.service;

import com.petlink.petlink_backend.DTO.MonitoreoRequest;
import com.petlink.petlink_backend.DTO.MonitoreoResponse;
import com.petlink.petlink_backend.entity.Mascota;
import com.petlink.petlink_backend.entity.Monitoreo;
import com.petlink.petlink_backend.repository.MascotaRepository;
import com.petlink.petlink_backend.repository.MonitoreoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Comparator;

@Service
public class MonitoreoService {

    private final MonitoreoRepository repo;
    private final MascotaRepository mascotaRepo;

    public MonitoreoService(MonitoreoRepository repo, MascotaRepository mascotaRepo) {
        this.repo = repo;
        this.mascotaRepo = mascotaRepo;
    }

    private MonitoreoResponse map(Monitoreo m) {
        return new MonitoreoResponse(
                m.getId(),
                m.getMascota().getId(),
                m.getMascota().getNombre(),
                m.getRitmoCardiaco(),
                m.getActividad(),
                m.getUbicacion(),
                m.getEstadoLED(),
                m.getUltimaActualizacion());
    }

    // Guarda datos enviados por la pechera
    public MonitoreoResponse crear(MonitoreoRequest req) {
        Mascota mascota = mascotaRepo.findById(req.getMascotaId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        Monitoreo m = new Monitoreo();
        m.setMascota(mascota);
        m.setRitmoCardiaco(req.getRitmoCardiaco());
        m.setActividad(req.getActividad());
        m.setUbicacion(req.getUbicacion());
        m.setEstadoLED(req.getEstadoLED());
        m.setUltimaActualizacion(LocalDateTime.now());

        repo.save(m);

        return map(m);
    }

    // Lista todas las lecturas
    public List<MonitoreoResponse> listar() {
        return repo.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    // Lista las lecturas por mascota
    public List<MonitoreoResponse> listarPorMascota(Long mascotaId) {
        return repo.findAll().stream()
                .filter(m -> m.getMascota().getId().equals(mascotaId))
                .map(this::map)
                .collect(Collectors.toList());
    }

    public List<MonitoreoResponse> listarUltimosPorMascota() {
        // Obtiene todos los registros
        List<Monitoreo> registros = repo.findAll();

        // Agrupa por mascotaId y se queda con el más reciente
        return registros.stream()
                .collect(Collectors.groupingBy(
                        m -> m.getMascota().getId(),
                        Collectors.maxBy(Comparator.comparing(Monitoreo::getUltimaActualizacion))))
                .values().stream()
                .filter(Optional::isPresent)
                .map(opt -> map(opt.get()))
                .collect(Collectors.toList());
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}