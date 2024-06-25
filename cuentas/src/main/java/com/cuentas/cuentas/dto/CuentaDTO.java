package com.cuentas.cuentas.dto;

import com.cuentas.cuentas.entity.Movimiento;
import com.cuentas.cuentas.enums.CuentaType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CuentaDTO
{
    private Integer id;

    @NotEmpty(message = "El campo numero de cuenta es obligatorio")
    @Pattern(regexp = "^[0-9]{6,11}$", message = "EL numero de cuenta no es valido, ingrese nuevamente")
    private String numeroCuenta;

    @NotNull(message = "El campo tipo de cuenta es obligatorio, por favor introduzcalo")
    @Enumerated(EnumType.STRING)
    private CuentaType tipoCuenta;

    @NotNull(message =  "El campo saldo incial es obligatorio, por favor introduzcalo")
    private Double saldoInicial;

    @NotNull(message = "El campo estado es obligatorio, por favor introduzcalo")
    private Boolean estado;

    @ToString.Exclude
    private ClienteDTO cliente;

}