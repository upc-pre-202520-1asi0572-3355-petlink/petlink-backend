package com.petlink.petlink_backend.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class MascotaRequest {
    private String nombre;
    private String especie;
    private int edad;
    private String estadoSalud;
    private String owner;
    private String raza;

    @JsonFormat(pattern = "hh:mm a")
    private String horaIngreso;

    private boolean internado;

    private Long collarId;
}