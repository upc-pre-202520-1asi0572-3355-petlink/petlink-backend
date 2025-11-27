package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.entity.Contacto;
import com.petlink.petlink_backend.service.ContactoService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contacto")
public class ContactoController {

    private final ContactoService contactoService;

    public ContactoController(ContactoService contactoService) {
        this.contactoService = contactoService;
    }

    // POST: Enviar mensaje
    @PostMapping
    public Contacto enviarMensaje(@RequestBody Contacto contacto) {
        return contactoService.guardar(contacto);
    }

    // GET: Listar mensajes (solo para admin)
    @GetMapping
    public List<Contacto> listarMensajes() {
        return contactoService.listar();
    }
}
