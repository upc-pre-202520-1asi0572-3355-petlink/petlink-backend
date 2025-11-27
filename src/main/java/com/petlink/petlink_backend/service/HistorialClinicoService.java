package com.petlink.petlink_backend.service;

import com.petlink.petlink_backend.DTO.HistorialClinicoRequest;
import com.petlink.petlink_backend.DTO.HistorialClinicoResponse;
import com.petlink.petlink_backend.entity.HistorialClinico;
import com.petlink.petlink_backend.entity.Mascota;
import com.petlink.petlink_backend.repository.HistorialRepository;
import com.petlink.petlink_backend.repository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistorialClinicoService {
    private final HistorialRepository repo;
    private final MascotaRepository mascotaRepo;

    public HistorialClinicoService(HistorialRepository repo, MascotaRepository mascotaRepo) {
        this.repo = repo;
        this.mascotaRepo = mascotaRepo;
    }

    public HistorialClinico guardarDesdeRequest(HistorialClinicoRequest req) {
        Mascota mascota = mascotaRepo.findById(req.getMascotaId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada con id " + req.getMascotaId()));

        HistorialClinico historial = new HistorialClinico();
        historial.setMascota(mascota);
        historial.setFecha(LocalDate.parse(req.getFecha()));
        historial.setDiagnostico(req.getDiagnostico());
        historial.setObservaciones(req.getObservaciones());

        return repo.save(historial);
    }

    public List<HistorialClinicoResponse> listar() {
        return repo.findAll().stream().map(h -> new HistorialClinicoResponse(
                h.getId(),
                h.getFecha().toString(),
                h.getDiagnostico(),
                h.getObservaciones(),
                h.getMascota().getNombre())).collect(Collectors.toList());
    }

    public Optional<HistorialClinico> obtenerPorId(Long id) {
        return repo.findById(id);
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}