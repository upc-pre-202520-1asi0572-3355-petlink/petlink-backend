package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.entity.Usuario;
import com.petlink.petlink_backend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/perfil")
@CrossOrigin(origins = "http://localhost:4200")
public class PerfilController {

    private final UsuarioService usuarioService;

    public PerfilController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Obtiene perfil por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getPerfil(@PathVariable Long id) {
        return usuarioService.obtenerUsuario(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Actualiza perfil
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updatePerfil(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuario));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}