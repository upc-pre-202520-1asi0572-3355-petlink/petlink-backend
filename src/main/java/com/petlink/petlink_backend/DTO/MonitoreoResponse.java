package com.petlink.petlink_backend.DTO;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonitoreoResponse {
    private Long id;
    private Long mascotaId;
    private String nombreMascota;
    private Integer ritmoCardiaco;
    private Integer actividad;
    private String ubicacion;
    private String estadoLED;
    private LocalDateTime ultimaActualizacion;
}