package com.cuentas.cuentas.service;

import com.cuentas.cuentas.dto.ReporteDTO;

import java.time.LocalDate;

public interface ReporteService
{
    ReporteDTO getReporte(Integer clienteId, LocalDate fechaInicial, LocalDate fechafinal);
}