// DTO para crear/actualizar (entrada)
package com.petlink.petlink_backend.DTO;

import lombok.Data;

@Data
public class HistorialClinicoRequest {
    private Long mascotaId;
    private String fecha;
    private String diagnostico;
    private String observaciones;
}
