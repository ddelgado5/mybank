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
public class ReporteDTO
{
    private Integer clienteId;
    private String nombre;
    private String identificacion;
    private List<CuentaReporteDTO> cuentas;
}