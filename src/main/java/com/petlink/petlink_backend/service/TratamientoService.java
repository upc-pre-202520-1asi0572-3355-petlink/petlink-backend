package com.petlink.petlink_backend.service;

import com.petlink.petlink_backend.DTO.TratamientoRequest;
import com.petlink.petlink_backend.DTO.TratamientoResponse;
import com.petlink.petlink_backend.entity.HistorialClinico;
import com.petlink.petlink_backend.entity.Mascota;
import com.petlink.petlink_backend.entity.Tratamiento;
import com.petlink.petlink_backend.repository.HistorialRepository;
import com.petlink.petlink_backend.repository.MascotaRepository;
import com.petlink.petlink_backend.repository.TratamientoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TratamientoService {

    private final TratamientoRepository repo;
    private final MascotaRepository mascotaRepo;
    private final HistorialRepository historialRepo;

    public TratamientoService(TratamientoRepository repo,
            MascotaRepository mascotaRepo,
            HistorialRepository historialRepo) {
        this.repo = repo;
        this.mascotaRepo = mascotaRepo;
        this.historialRepo = historialRepo;
    }

    // Helpers
    private TratamientoResponse map(Tratamiento t) {
        return new TratamientoResponse(
                t.getId(),
                t.getMascota().getId(),
                t.getMascota().getNombre(),
                t.getHistorialClinico() != null ? t.getHistorialClinico().getId() : null,
                t.getNombreTratamiento(),
                t.getDescripcion(),
                t.getFechaInicio(),
                t.getFechaFin(),
                t.getEstado());
    }

    private Tratamiento fromRequest(TratamientoRequest req, Tratamiento target) {
        Mascota mascota = mascotaRepo.findById(req.getMascotaId())
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada: id " + req.getMascotaId()));

        HistorialClinico hc = null;
        if (req.getHistorialId() != null) {
            hc = historialRepo.findById(req.getHistorialId())
                    .orElseThrow(() -> new RuntimeException("Historial no encontrado: id " + req.getHistorialId()));
        }

        target.setMascota(mascota);
        target.setHistorialClinico(hc);
        target.setNombreTratamiento(req.getNombreTratamiento());
        target.setDescripcion(req.getDescripcion());
        target.setFechaInicio(req.getFechaInicio());
        target.setFechaFin(req.getFechaFin());
        target.setEstado(req.getEstado());
        return target;
    }

    // CRUD
    public TratamientoResponse crear(TratamientoRequest req) {
        Tratamiento nuevo = fromRequest(req, new Tratamiento());
        return map(repo.save(nuevo));
    }

    public List<TratamientoResponse> listarTodos() {
        return repo.findAll().stream().map(this::map).collect(Collectors.toList());
    }

    public List<TratamientoResponse> listarActivos() {
        return repo.findByEstado("Activo").stream().map(this::map).collect(Collectors.toList());
    }

    public TratamientoResponse actualizar(Long id, TratamientoRequest req) {
        Tratamiento ex = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado: id " + id));
        fromRequest(req, ex);
        return map(repo.save(ex));
    }

    public TratamientoResponse cambiarEstado(Long id, String estado) {
        Tratamiento ex = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado: id " + id));
        ex.setEstado(estado);
        return map(repo.save(ex));
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}