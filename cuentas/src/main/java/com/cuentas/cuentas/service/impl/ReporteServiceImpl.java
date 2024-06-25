package com.cuentas.cuentas.service.impl;

import com.cuentas.cuentas.dto.CuentaReporteDTO;
import com.cuentas.cuentas.dto.MovimientoReporteDTO;
import com.cuentas.cuentas.dto.ReporteDTO;
import com.cuentas.cuentas.entity.Cliente;
import com.cuentas.cuentas.entity.Cuenta;
import com.cuentas.cuentas.entity.Movimiento;
import com.cuentas.cuentas.api.ClienteServiceApi;
import com.cuentas.cuentas.service.CuentaService;
import com.cuentas.cuentas.service.ReporteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@Service
@AllArgsConstructor
public class ReporteServiceImpl implements ReporteService
{
    private ClienteServiceApi clienteServiceApi;
    private CuentaService cuentaService;

    @Override
    public ReporteDTO getReporte(Integer clienteId, LocalDate fechaInicial, LocalDate fechafinal)
    {
        Cliente cliente = clienteServiceApi.getClienteById(clienteId);
        cliente.setCuentas(getCuentasByCliente(cliente, fechaInicial, fechafinal));
        log.info(String.format("Generando repore! del cliente %s desde %s hasta %s", cliente.getNombre(), fechafinal.toString(),
                fechafinal.toString()));
        return ReporteDTO.builder().clienteId(cliente.getClienteId()).nombre(cliente.getNombre())
                .identificacion(cliente.getIdentificacion()).cuentas(getCuentas(cliente.getCuentas())).build();

    }

    private List<CuentaReporteDTO> getCuentas(List<Cuenta> cuentas)
    {

        return cuentas.stream().map(cuenta -> {
            double totalDebitos = cuenta.getMovimientos().stream().filter(m -> "Debito".equals(m.getTipoMovimiento().getValue()))
                    .mapToDouble(Movimiento::getValor).sum();

            double totalCreditos = cuenta.getMovimientos().stream()
                    .filter(m -> "Credito".equals(m.getTipoMovimiento().getValue())).mapToDouble(Movimiento::getValor).sum();
            return CuentaReporteDTO.builder().numeroCuenta(cuenta.getNumeroCuenta()).tipoCuenta(cuenta.getTipoCuenta().getValue())
                    .movimientos(getMovimientos(cuenta.getMovimientos())).totalDebitos(totalDebitos).totalCreditos(totalCreditos)
                    .saldoInicial(cuenta.getSaldoInicial()).saldo(cuenta.getSaldoInicial() + (totalCreditos - totalDebitos))
                    .build();
        }).collect(Collectors.toList());
    }

    private List<MovimientoReporteDTO> getMovimientos(List<Movimiento> movimientos)
    {
        return movimientos.stream().map(movimiento -> {
            return MovimientoReporteDTO.builder().tipoMovimiento(movimiento.getTipoMovimiento()).fecha(movimiento.getFecha())
                    .valor(movimiento.getValor()).saldo(movimiento.getSaldo()).build();
        }).collect(Collectors.toList());
    }

    private List<Cuenta> getCuentasByCliente(Cliente cliente, LocalDate fechaInicial, LocalDate fechaFinal)
    {
        List<Cuenta> cuentas = cuentaService.findAllByCliente(cliente);
        return cuentas.stream().map(cuenta -> {
            List<Movimiento> movimientosFiltrados = cuenta.getMovimientos().stream()
                    .filter(movimiento -> !movimiento.getFecha().toLocalDate().isBefore(fechaInicial) && !movimiento.getFecha()
                            .toLocalDate().isAfter(fechaFinal)).collect(Collectors.toList());
            cuenta.setMovimientos(movimientosFiltrados);
            return cuenta;
        }).collect(Collectors.toList());
    }
}