package com.petlink.petlink_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contactos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String asunto;

    @Column(length = 1000)
    private String mensaje;
}