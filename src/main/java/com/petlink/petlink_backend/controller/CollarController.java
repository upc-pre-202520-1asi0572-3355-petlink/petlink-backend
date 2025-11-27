package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.entity.Collar;
import com.petlink.petlink_backend.service.CollarService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/collares")
public class CollarController {

    private final CollarService service;

    public CollarController(CollarService service) {
        this.service = service;
    }

    @PostMapping
    public Collar crear(@RequestBody Collar c) {
        return service.crear(c);
    }

    @GetMapping("/disponibles")
    public List<Collar> disponibles() {
        return service.listarDisponibles();
    }
}