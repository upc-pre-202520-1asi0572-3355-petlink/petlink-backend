package com.petlink.petlink_backend.service;

import com.petlink.petlink_backend.DTO.MonitoreoRequest;
import com.petlink.petlink_backend.DTO.MonitoreoResponse;
import com.petlink.petlink_backend.DTO.PetMonitoringFullResponse;
import com.petlink.petlink_backend.entity.Mascota;
import com.petlink.petlink_backend.entity.Monitoreo;
import com.petlink.petlink_backend.repository.MascotaRepository;
import com.petlink.petlink_backend.repository.MonitoreoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public PetMonitoringFullResponse getMonitoringView(Long mascotaId) {

        Mascota mascota = mascotaRepo.findById(mascotaId)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        List<MonitoreoResponse> registros = listarPorMascota(mascotaId);

        // ordena los más recientes primero
        registros.sort((a, b) -> b.getUltimaActualizacion().compareTo(a.getUltimaActualizacion()));

        // normaliza los datos de mascota
        // normaliza los datos de mascota
        String owner = mascota.getOwner() != null ? mascota.getOwner() : "Sin dueño";
        String breed = mascota.getRaza() != null ? mascota.getRaza() : "Desconocida";

        String admission = mascota.getHoraIngreso() != null
                ? mascota.getHoraIngreso().format(DateTimeFormatter.ofPattern("hh:mm a"))
                : "No registrado";

        String edadStr = mascota.getEdad() > 0 ? mascota.getEdad() + " años" : "N/A";

        // Si no hay registros devuelve default
        if (registros.isEmpty()) {
            return new PetMonitoringFullResponse(
                    mascota.getNombre(),
                    owner,
                    edadStr,
                    breed,
                    admission,
                    0,
                    "Sin Data",
                    List.of());
        }

        // registros -> muestra los últimos 6
        Integer currentHR = registros.get(0).getRitmoCardiaco();

        String status;
        if (currentHR >= 100)
            status = "Crítico";
        else if (currentHR >= 80)
            status = "Alerta";
        else
            status = "Estable";

        List<Integer> history = registros.stream()
                .limit(6)
                .map(MonitoreoResponse::getRitmoCardiaco)
                .toList();

        return new PetMonitoringFullResponse(
                mascota.getNombre(),
                owner,
                edadStr,
                breed,
                admission,
                currentHR,
                status,
                history);
    }
}