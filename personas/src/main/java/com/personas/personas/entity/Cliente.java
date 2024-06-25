package com.personas.personas.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Persona
{
    @NotEmpty(message = "El campo contraseña es obligatorio por favor introduzca")
    private String contrasena;

    @NotNull(message = "El campo estado es obligatorio por favor introduzca")
    private Boolean estado;
}