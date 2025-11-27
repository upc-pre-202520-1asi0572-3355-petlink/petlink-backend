package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.entity.Collar;
import com.petlink.petlink_backend.repository.CollarRepository;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/collare")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CollarController {

    private final CollarRepository collarRepo;

    /** Listar todos los collares */
    @GetMapping
    public List<Collar> listar() {
        return collarRepo.findAll();
    }

    /** Listar collares disponibles */
    @GetMapping("/disponibles")
    public List<Collar> disponibles() {
        return collarRepo.findByEstado("DISPONIBLE");
    }

    /** Crear collar */
    @PostMapping
    public Collar crear(@RequestBody Collar c) {
        c.setEstado("DISPONIBLE");
        return collarRepo.save(c);
    }
}