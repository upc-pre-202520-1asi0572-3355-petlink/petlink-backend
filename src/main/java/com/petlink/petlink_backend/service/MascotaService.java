package com.petlink.petlink_backend.service;

import com.petlink.petlink_backend.entity.Mascota;
import com.petlink.petlink_backend.repository.MascotaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MascotaService {
    private final MascotaRepository mascotaRepository;

    public MascotaService(MascotaRepository mascotaRepository) {
        this.mascotaRepository = mascotaRepository;
    }

    public List<Mascota> listarMascotas() {
        return mascotaRepository.findAll();
    }

    public Mascota guardarMascota(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    public Optional<Mascota> actualizarMascota(Long id, Mascota mascotaDetalles) {
        return mascotaRepository.findById(id).map(mascota -> {

            mascota.setNombre(mascotaDetalles.getNombre());
            mascota.setEspecie(mascotaDetalles.getEspecie());
            mascota.setEdad(mascotaDetalles.getEdad());
            mascota.setEstadoSalud(mascotaDetalles.getEstadoSalud());
            mascota.setOwner(mascotaDetalles.getOwner());
            mascota.setRaza(mascotaDetalles.getRaza());
            mascota.setHoraIngreso(mascotaDetalles.getHoraIngreso());

            return mascotaRepository.save(mascota);
        });
    }

    public boolean eliminarMascota(Long id) {
        if (mascotaRepository.existsById(id)) {
            mascotaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}