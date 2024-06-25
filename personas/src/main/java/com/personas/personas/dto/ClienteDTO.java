package com.personas.personas.dto;

import com.personas.personas.enums.GeneroType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClienteDTO
{
    private Integer clienteId;

    @NotEmpty(message = "El campo nombre es obligatorio por favor introduzca")
    private String nombre;

    @NotNull(message = "El campo genero es obligatorio escoja entre MASCULINO, FEMENINO o NO_DEFINE ")
    @Enumerated(EnumType.STRING)
    private GeneroType genero;

    @Min(18)
    @Max(100)
    private byte edad;

    @NotEmpty(message = "El campo identificacion es obligatorio por favor introduzca")
    @Column(unique = true)
    @Pattern(regexp = "^[0-9]{10,13}$",
            message = "Numero de identificación incorrecto, la identificación debe contener entre 10 y 13 digitos")
    private String identificacion;

    @NotEmpty(message = "El campo dirreción es obligatorio, por favor intorduzca")
    private String direccion;

    @NotEmpty(message = "El campo telefono es obligatorio, por favor intorduzca")
    @Pattern(regexp = "^[0-9]{9,13}$",
            message = "Numero de telefono incorrecto, el telefono debe contener entre 9 y 13 digitos")
    private String telefono;

    @NotNull(message = "El campo estado es obligatorio por favor introduzca")
    private Boolean estado;

    @NotEmpty(message = "El campo contraseña es obligatorio por favor introduzca")
    private String contrasena;
}