package com.petlink.petlink_backend.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "mascotas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // id solo lectura
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La especie no puede estar vacía")
    @Pattern(regexp = "Perro|Gato|Ave|Otro", message = "La especie debe ser Perro, Gato, Ave u Otro")
    @Column(nullable = false)
    private String especie;

    @Min(value = 0, message = "La edad no puede ser negativa")
    @Max(value = 50, message = "La edad máxima permitida es 50")
    private int edad;

    @NotBlank(message = "El estado de salud no puede estar vacío")
    @Pattern(regexp = "Estable|En tratamiento|Critico", message = "El estado de salud debe ser Estable, En tratamiento o Critico")
    private String estadoSalud;

    @NotBlank
    private String owner; // nombre del dueño

    @NotBlank
    private String raza;

    private String horaIngreso; // formato "10:30 AM"

    private boolean internado = false;

    @OneToOne
    private Collar collarAsignado;
}