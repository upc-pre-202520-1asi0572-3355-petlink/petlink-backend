package com.petlink.petlink_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "tratamientos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Mascota obligatoria
    @ManyToOne(optional = false)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    // Historial clínico opcional (puede nulo)
    @ManyToOne(optional = true)
    @JoinColumn(name = "historial_id")
    private HistorialClinico historialClinico;

    @Column(nullable = false, length = 100)
    private String nombreTratamiento; // ej.: "Antibiótico"

    @Column(columnDefinition = "text")
    private String descripcion; // dosis, pauta, notas

    @Column(nullable = false)
    private LocalDate fechaInicio;

    private LocalDate fechaFin; // puede ser null mientras siga activo

    @Column(nullable = false, length = 20)
    private String estado = "Activo"; // Activo | Finalizado | Suspendido
}