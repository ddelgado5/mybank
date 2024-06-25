package com.cuentas.cuentas.dto;

import com.cuentas.cuentas.enums.MovimientoType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovimientoDTO
{
    private Integer id;
    private LocalDateTime fecha;

    @NotNull(message = "El campo tipo de movimiento es obligatorio, seleccione AHORRO o CORRIENTE")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento")
    private MovimientoType tipoMovimiento;

    @NotNull(message = "EL campo valor es obligatorio")
    private Double valor;

    private Double saldo;

    private CuentaDTO cuenta;
}