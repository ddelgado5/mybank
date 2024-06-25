package com.cuentas.cuentas.dto;

import com.cuentas.cuentas.enums.MovimientoType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class MovimientoReporteDTO
{
    private MovimientoType tipoMovimiento;
    private LocalDateTime fecha;
    private Double valor;
    private Double saldo;
}