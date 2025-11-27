package com.petlink.petlink_backend.service;

import com.petlink.petlink_backend.entity.Usuario;
import com.petlink.petlink_backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // LISTAR
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // OBTENER POR ID
    public Optional<Usuario> obtenerUsuario(Long id) {
        return usuarioRepository.findById(id);
    }

    // GUARDAR
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // ALIAS de guardar para AuthController
    public Usuario guardar(Usuario usuario) {
        return guardarUsuario(usuario);
    }

    // ACTUALIZAR
    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(u -> {
                    u.setNombre(usuario.getNombre());
                    u.setEmail(usuario.getEmail());
                    u.setRol(usuario.getRol());
                    u.setEstado(usuario.getEstado());
                    u.setPassword(usuario.getPassword());
                    return usuarioRepository.save(u);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    // ELIMINAR
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Buscar por email
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}