package com.cuentas.cuentas.service.impl;

import com.cuentas.cuentas.dto.MovimientoDTO;
import com.cuentas.cuentas.entity.Cuenta;
import com.cuentas.cuentas.entity.Movimiento;
import com.cuentas.cuentas.mapper.MovimientoMapper;
import com.cuentas.cuentas.repository.MovimientoRepository;
import com.cuentas.cuentas.service.CuentaService;
import com.cuentas.cuentas.service.MovimientoService;
import com.cuentas.cuentas.util.BadRequestException;
import com.cuentas.cuentas.util.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class MovimientoServiceImpl implements MovimientoService
{
    public static final String SALDO_NO_DISPONIBLE = "Saldo no disponible";
    public static final String CUPO_DIARIO_EXCEDIDO = "Cupo diario Excedido";

    private MovimientoRepository movimientoRepository;
    private CuentaService cuentaService;
    private MovimientoMapper movimientoMapper;

    @Override
    public List<MovimientoDTO> findAll()
    {
        return movimientoRepository.findAll().stream().map(movimientoMapper::movimientoToMoviemientoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MovimientoDTO findById(Integer id)
    {
        Optional<Movimiento> movimiento = movimientoRepository.findById(id);
        if (movimiento.isPresent())
        {
            return movimiento.map(movimientoMapper::movimientoToMoviemientoDTO).get();
        }
        else
        {
            log.error("Movimiento no encontrado por ID: {}", id);
            throw new NotFoundException(String.format("No se encontró el movimiento con el ID: %d", id));
        }
    }

    @Override
    public MovimientoDTO create(MovimientoDTO movimientoDTO)
    {
        log.info("Inicio de la creación del movimiento");
        final Optional<Cuenta> cuenta = cuentaService.findByNumeroCuenta(movimientoDTO.getCuenta().getNumeroCuenta());
        if (cuenta.isPresent())
        {
            final double total;
            if (cuenta.get().getMovimientos().isEmpty())
            {
                total = createMovimiento(movimientoDTO.getTipoMovimiento().getValue(), cuenta.get().getSaldoInicial(),
                        movimientoDTO.getValor());
            }
            else
            {
                total = createMovimiento(movimientoDTO.getTipoMovimiento().getValue(),
                        getUltimoMovimiento(cuenta.get().getMovimientos()), movimientoDTO.getValor());
            }
            final Movimiento movimiento = movimientoMapper.movimientoDTOToMovimiento(movimientoDTO);
            movimiento.setFecha(LocalDateTime.now());
            movimiento.setCuenta(cuenta.get());
            movimiento.setSaldo(total);
            return movimientoMapper.movimientoToMoviemientoDTO(movimientoRepository.save(movimiento));
        }
        else
        {
            log.error("Cuenta no encontrada por número: {}", movimientoDTO.getCuenta().getNumeroCuenta());
            throw new NotFoundException(String.format("No se encontró la cuenta con el número: %s",
                    movimientoDTO.getCuenta().getNumeroCuenta()));
        }
    }

    private double createMovimiento(final String tipoMovimiento, final double saldo, final double valor)
    {
        double saldoNuevo = saldo;
        switch (tipoMovimiento)
        {
            case "Debito":
                saldoNuevo -= valor;
                break;
            case "Credito":
                saldoNuevo += valor;
                break;
        }

        if (saldoNuevo < 0)
        {
            log.error(SALDO_NO_DISPONIBLE);
            throw new BadRequestException(SALDO_NO_DISPONIBLE);
        }
        return saldoNuevo;
    }

    @Override
    public MovimientoDTO update(final MovimientoDTO movimientoDTO)
    {
        final Optional<Movimiento> updateMovimientoOpt = movimientoRepository.findById(movimientoDTO.getId());
        if (updateMovimientoOpt.isPresent())
        {
            final Movimiento updateMovimiento = updateMovimientoOpt.get();
            Optional<Cuenta> cuentaOpt = cuentaService.findByNumeroCuenta(movimientoDTO.getCuenta().getNumeroCuenta());
            double saldo = getSaldo(updateMovimiento, cuentaOpt);

            updateMovimiento.setSaldo(
                    createMovimiento(movimientoDTO.getTipoMovimiento().getValue(), saldo, movimientoDTO.getValor()));
            updateMovimiento.setTipoMovimiento(movimientoDTO.getTipoMovimiento());
            updateMovimiento.setValor(movimientoDTO.getValor());

            return movimientoMapper.movimientoToMoviemientoDTO(movimientoRepository.save(updateMovimiento));
        }
        else
        {
            log.error("Movimiento no encontrado por ID: {}", movimientoDTO.getId());
            throw new NotFoundException(String.format("No se encontró el movimiento con el ID: %d", movimientoDTO.getId()));
        }
    }

    @Override
    public void delete(Integer id)
    {
        movimientoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Movimiento no encontrado por ID: %d", id)));
        movimientoRepository.deleteById(id);
        log.info("Movimiento eliminado con éxito con el ID: {}", id);
    }

    private double getSaldo(Movimiento updateMovimiento, Optional<Cuenta> cuentaOpt)
    {
        double saldo = cuentaOpt.map(cuenta -> {
            double saldoNuevo = getUltimoMovimiento(cuenta.getMovimientos());
            if (updateMovimiento.getTipoMovimiento().getValue().equals("Debito"))
            {
                saldoNuevo += updateMovimiento.getValor();
            }
            else if (updateMovimiento.getTipoMovimiento().getValue().equals("Credito"))
            {
                saldoNuevo -= updateMovimiento.getValor();
            }
            return saldoNuevo;
        }).orElse(0.0);
        return saldo;
    }

    public double getUltimoMovimiento(List<Movimiento> movimientos)
    {
        return movimientos.isEmpty() ? 0 : movimientos.get(movimientos.size() - 1).getSaldo();
    }

}