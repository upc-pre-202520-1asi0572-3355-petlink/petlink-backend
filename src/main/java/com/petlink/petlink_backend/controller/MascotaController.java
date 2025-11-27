package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.DTO.MascotaRequest;
import com.petlink.petlink_backend.entity.Collar;
import com.petlink.petlink_backend.entity.Mascota;
import com.petlink.petlink_backend.repository.CollarRepository;
import com.petlink.petlink_backend.repository.MascotaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/mascotas")
@RequiredArgsConstructor
@CrossOrigin("*")
public class MascotaController {

    private final MascotaRepository mascotaRepo;
    private final CollarRepository collarRepo;

    /**
     * Listar todas las mascotas
     */
    @GetMapping
    public List<Mascota> listar() {
        return mascotaRepo.findAll();
    }

    /**
     * Crear mascota con collarId
     */
    @PostMapping
    public ResponseEntity<?> crearMascota(@RequestBody MascotaRequest request) {

        Mascota mascota = new Mascota();
        mascota.setNombre(request.getNombre());
        mascota.setEspecie(request.getEspecie());
        mascota.setEdad(request.getEdad());
        mascota.setEstadoSalud(request.getEstadoSalud());
        mascota.setOwner(request.getOwner());
        mascota.setRaza(request.getRaza());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime hora = LocalTime.parse(request.getHoraIngreso(), formatter);
        mascota.setHoraIngreso(hora);

        mascota.setInternado(request.isInternado());

        // Asignar collar si vino en el request
        if (request.getCollarId() != null) {
            Collar collar = collarRepo.findById(request.getCollarId())
                    .orElseThrow(() -> new RuntimeException("Collar no encontrado"));

            if (!"DISPONIBLE".equalsIgnoreCase(collar.getEstado())) {
                return ResponseEntity.badRequest().body("El collar está asignado");
            }

            mascota.setCollarAsignado(collar);
            collar.setEstado("ASIGNADO");
            collar.setMascotaAsignada(mascota);
            collarRepo.save(collar);
        }

        mascotaRepo.save(mascota);
        return ResponseEntity.ok(mascota);
    }

    /**
     * Actualizar información de mascota
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMascota(@PathVariable Long id, @RequestBody MascotaRequest request) {

        Mascota mascota = mascotaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        mascota.setNombre(request.getNombre());
        mascota.setEspecie(request.getEspecie());
        mascota.setEdad(request.getEdad());
        mascota.setEstadoSalud(request.getEstadoSalud());
        mascota.setOwner(request.getOwner());
        mascota.setRaza(request.getRaza());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime hora = LocalTime.parse(request.getHoraIngreso(), formatter);
        mascota.setHoraIngreso(hora);

        mascota.setInternado(request.isInternado());

        // reasignar collar si es necesario
        if (request.getCollarId() != null) {
            Collar collar = collarRepo.findById(request.getCollarId())
                    .orElseThrow(() -> new RuntimeException("Collar no encontrado"));

            mascota.setCollarAsignado(collar);
            collar.setEstado("ASIGNADO");
            collar.setMascotaAsignada(mascota);
            collarRepo.save(collar);
        }

        mascotaRepo.save(mascota);
        return ResponseEntity.ok(mascota);
    }

    /**
     * Eliminar mascota y liberar el collar
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Mascota mascota = mascotaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        // liberar collar en caso exista
        if (mascota.getCollarAsignado() != null) {
            Collar collar = mascota.getCollarAsignado();
            collar.setEstado("DISPONIBLE");
            collar.setMascotaAsignada(null);
            collarRepo.save(collar);
        }

        mascotaRepo.delete(mascota);
        return ResponseEntity.ok("Mascota eliminada");
    }
}