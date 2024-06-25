package com.cuentas.cuentas.mapper;

import com.cuentas.cuentas.dto.CuentaDTO;
import com.cuentas.cuentas.entity.Cuenta;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CuentaMapper
{
    CuentaDTO cuentaToCuentaDTO(final Cuenta cuenta);
    Cuenta cuentaDTOToCuenta(final CuentaDTO cuentaDTO);
}