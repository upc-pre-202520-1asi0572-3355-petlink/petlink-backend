package com.petlink.petlink_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Collar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo; // Identificador físico
    private String modelo; // Modelo o tipo
    private String estado; // DISPONIBLE / ASIGNADO
    private String descripcion;

    @OneToOne
    private Mascota mascotaAsignada;
}