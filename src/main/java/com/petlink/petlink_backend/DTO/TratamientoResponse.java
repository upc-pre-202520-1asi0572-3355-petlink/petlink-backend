package com.petlink.petlink_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TratamientoResponse {
    private Long id;
    private Long mascotaId;
    private String nombreMascota;
    private Long historialId;
    private String nombreTratamiento;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
}