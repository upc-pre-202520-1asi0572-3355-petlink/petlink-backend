package com.petlink.petlink_backend.service;

import com.petlink.petlink_backend.entity.Collar;
import com.petlink.petlink_backend.entity.Mascota;
import com.petlink.petlink_backend.repository.MascotaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;

    @Autowired
    private CollarService collarService;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    // Listar mascotas
    public List<Mascota> listarMascotas() {
        return mascotaRepository.findAll();
    }

    // Crear nueva mascota
    public Mascota guardarMascota(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    // Actualizar mascota
    public Optional<Mascota> actualizarMascota(Long id, Mascota mascotaDetalles) {
        return mascotaRepository.findById(id).map(m -> {

            m.setNombre(mascotaDetalles.getNombre());
            m.setEspecie(mascotaDetalles.getEspecie());
            m.setEdad(mascotaDetalles.getEdad());
            m.setEstadoSalud(mascotaDetalles.getEstadoSalud());
            m.setOwner(mascotaDetalles.getOwner());
            m.setRaza(mascotaDetalles.getRaza());
            m.setHoraIngreso(mascotaDetalles.getHoraIngreso());

            return mascotaRepository.save(m);
        });
    }

    // Eliminar mascota
    public boolean eliminarMascota(Long id) {
        if (mascotaRepository.existsById(id)) {
            mascotaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // INTERNAR MASCOTA Y ASIGNAR COLLAR
    public Mascota internar(Long idMascota, Long idCollar) {

        Mascota mascota = mascotaRepository.findById(idMascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        Collar collar = collarService.buscar(idCollar);

        collarService.asignarCollar(idCollar, mascota);

        mascota.setInternado(true);
        mascota.setCollarAsignado(collar);

        return mascotaRepository.save(mascota);
    }

    // DAR DE ALTA Y LIBERAR COLLAR
    public Mascota darAlta(Long idMascota) {

        Mascota mascota = mascotaRepository.findById(idMascota)
                .orElseThrow(() -> new RuntimeException("Mascota no encontrada"));

        collarService.liberarCollar(mascota);

        mascota.setInternado(false);
        mascota.setCollarAsignado(null);

        return mascotaRepository.save(mascota);
    }
}