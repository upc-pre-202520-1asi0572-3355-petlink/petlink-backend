package com.petlink.petlink_backend.service;

import com.petlink.petlink_backend.entity.Collar;
import com.petlink.petlink_backend.entity.Mascota;
import com.petlink.petlink_backend.repository.CollarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollarService {

    private final CollarRepository repo;

    public CollarService(CollarRepository repo) {
        this.repo = repo;
    }

    public Collar crear(Collar c) {
        c.setEstado("DISPONIBLE");
        return repo.save(c);
    }

    public List<Collar> listarDisponibles() {
        return repo.findAll()
                .stream()
                .filter(c -> c.getEstado().equals("DISPONIBLE"))
                .toList();
    }

    public Collar buscar(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public void asignarCollar(Long idCollar, Mascota m) {
        Collar c = buscar(idCollar);
        c.setMascotaAsignada(m);
        c.setEstado("ASIGNADO");
        repo.save(c);
    }

    public void liberarCollar(Mascota m) {
        repo.findAll().forEach(c -> {
            if (c.getMascotaAsignada() != null &&
                    c.getMascotaAsignada().getId().equals(m.getId())) {
                c.setMascotaAsignada(null);
                c.setEstado("DISPONIBLE");
                repo.save(c);
            }
        });
    }
}