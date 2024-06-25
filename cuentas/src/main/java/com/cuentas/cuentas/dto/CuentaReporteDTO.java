package com.cuentas.cuentas.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class CuentaReporteDTO
{
    private String numeroCuenta;
    private String tipoCuenta;
    private List<MovimientoReporteDTO> movimientos;
    private Double saldoInicial;
    private Double totalCreditos;
    private Double totalDebitos;
    private Double saldo;
}