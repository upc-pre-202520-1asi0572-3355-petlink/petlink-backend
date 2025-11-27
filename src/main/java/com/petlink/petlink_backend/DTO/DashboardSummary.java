package com.petlink.petlink_backend.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummary {
    private long totalMascotas;
    private long criticos;
    private long estables;
    private long alertas;
}