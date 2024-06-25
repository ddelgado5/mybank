package com.personas.personas.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Persona
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cliente_id")
    private Integer clienteId;

    @NotEmpty(message = "El campo nombre es obligatorio por favor introduzca")
    private String nombre;

    @NotEmpty(message = "El campo genero es obligatorio escoja entre MASCULINO, FEMENINO o NO_IDENTIFICA ")
    private String genero;

    @Min(18)
    @Max(100)
    private byte edad;

    @NotEmpty(message = "El campo identificacion es obligatorio por favor introduzca")
    @Column(unique = true)
    @Pattern(regexp = "^[0-9]{10,13}$",
            message = "Numero de identificación incorrecto, la identificación debe contener entre 10 y 13 digitos")
    private String identificacion;

    @NotEmpty(message = "El campo dirección es obligatorio, por favor intorduzca")
    private String direccion;

    @NotEmpty(message = "El campo telefono es obligatorio, por favor intorduzca")
    @Pattern(regexp = "^[0-9]{9,13}$",
            message = "Numero de telefono incorrecto, el telefono debe contener entre 10 y 13 digitos")
    private String telefono;
}