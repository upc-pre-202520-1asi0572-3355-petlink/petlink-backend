
package com.petlink.petlink_backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HistorialClinicoResponse {
    private Long id;
    private String fecha;
    private String diagnostico;
    private String observaciones;
    private String nombreMascota;
}