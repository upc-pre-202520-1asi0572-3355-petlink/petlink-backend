package com.petlink.petlink_backend.controller;

import com.petlink.petlink_backend.entity.Usuario;
import com.petlink.petlink_backend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Registro
    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
        if (usuarioService.buscarPorEmail(usuario.getEmail()) != null) {
            return ResponseEntity.badRequest().body(null); // Ya existe
        }
        Usuario nuevo = usuarioService.guardar(usuario);
        return ResponseEntity.ok(nuevo);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        Usuario existente = usuarioService.buscarPorEmail(usuario.getEmail());

        if (existente == null) {
            return ResponseEntity.status(401).body("Usuario no encontrado");
        }

        if (existente.getPassword() == null || !existente.getPassword().equals(usuario.getPassword())) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }

        return ResponseEntity.ok("Login exitoso");
    }

    // Recupera la contraseña (simula envío de enlace)
    @PostMapping("/recover-password")
    public ResponseEntity<Map<String, String>> recoverPassword(@RequestBody Usuario usuario) {
        Usuario existente = usuarioService.buscarPorEmail(usuario.getEmail());

        if (existente == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }

        return ResponseEntity.ok(Map.of(
                "message", "Se ha enviado un enlace de recuperación al correo: " + usuario.getEmail()));
    }

    // Resetea la contraseña (cambia la contraseña real en la DB)
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestParam String email,
            @RequestParam String newPassword) {
        Usuario existente = usuarioService.buscarPorEmail(email);

        if (existente == null) {
            return ResponseEntity.status(404).body(Map.of("error", "Usuario no encontrado"));
        }

        existente.setPassword(newPassword);
        usuarioService.guardar(existente);

        return ResponseEntity.ok(Map.of("message", "La contraseña se actualizó correctamente"));
    }
}