package com.petlink.petlink_backend.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonitoreoRequest {
    private Long mascotaId;
    private Integer ritmoCardiaco;
    private Integer actividad;
    private String ubicacion;
    private String estadoLED; // Verde | Amarillo | Rojo
}