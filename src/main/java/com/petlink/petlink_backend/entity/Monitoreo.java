package com.petlink.petlink_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "monitoreo_iot")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Monitoreo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con la mascota internada
    @ManyToOne(optional = false)
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;

    @Column(name = "ritmo_cardiaco")
    private Integer ritmoCardiaco; // bpm

    @Column(nullable = false)
    private Integer actividad; // %

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(nullable = false, length = 50)
    private String ubicacion;

    @Column(name = "estadoLED")
    private String estadoLED; // Verde, Amarillo, Rojo

    @Column(name = "ultimaActualizacion")
    private LocalDateTime ultimaActualizacion;
}