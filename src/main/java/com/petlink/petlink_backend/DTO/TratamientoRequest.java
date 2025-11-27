package com.petlink.petlink_backend.DTO;

import lombok.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class TratamientoRequest {
    @NotNull
    private Long mascotaId;

    private Long historialId; // opcional

    @NotBlank
    private String nombreTratamiento;

    private String descripcion;

    @NotNull
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    @NotBlank
    private String estado; // "Activo" | "Finalizado" | "Suspendido"
}