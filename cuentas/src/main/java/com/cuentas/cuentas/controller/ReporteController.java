package com.cuentas.cuentas.controller;

import com.cuentas.cuentas.dto.ReporteDTO;
import com.cuentas.cuentas.service.ReporteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RestController
@RequestMapping("/reportes")
public class ReporteController
{
    @Autowired
    private ReporteService reporteService;

    @GetMapping
    public ResponseEntity<ReporteDTO> getReporte(@Valid @RequestParam("clienteId") Integer clienteId,
                                                 @RequestParam("fechaInicial") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicial,
                                                 @RequestParam("fechaFinal") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFinal)
    {
        return ResponseEntity.status(HttpStatus.OK).body(reporteService.getReporte(clienteId, fechaInicial, fechaFinal));
    }
}
