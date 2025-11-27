package com.petlink.petlink_backend.DTO;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetMonitoringFullResponse {

        private String nombreMascota;
        private String owner;
        private String edad;
        private String raza;
        private String horaIngreso;
        private Integer ritmoCardiacoActual;
        private String estado;
        private List<Integer> history;
}