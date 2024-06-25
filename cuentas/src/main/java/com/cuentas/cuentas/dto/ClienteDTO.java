package com.cuentas.cuentas.dto;

import com.cuentas.cuentas.entity.Cuenta;
import com.cuentas.cuentas.enums.GeneroType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDTO
{
    private Integer clienteId;

    @NotEmpty(message = "El campo nombre es obligatorio por favor introduzca")
    private String nombre;

    @NotNull(message = "El campo genero es obligatorio escoja entre MASCULINO, FEMENINO o NO_IDENTIFICA")
    @Enumerated(EnumType.STRING)
    private String genero;

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