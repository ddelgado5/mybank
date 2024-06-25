package com.cuentas.cuentas.mapper;

import com.cuentas.cuentas.dto.MovimientoDTO;
import com.cuentas.cuentas.entity.Movimiento;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MovimientoMapper
{
    MovimientoDTO movimientoToMoviemientoDTO(final Movimiento movimiento);
    Movimiento movimientoDTOToMovimiento(final MovimientoDTO movimientoDTO);
}