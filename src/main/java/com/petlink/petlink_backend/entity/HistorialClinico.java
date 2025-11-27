package com.petlink.petlink_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Getter
@Setter
@Table(name = "historiales_clinicos")
public class HistorialClinico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔹 Relación correcta: un historial pertenece a una mascota
    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Mascota mascota;

    private LocalDate fecha;
    private String diagnostico;
    private String observaciones;
}