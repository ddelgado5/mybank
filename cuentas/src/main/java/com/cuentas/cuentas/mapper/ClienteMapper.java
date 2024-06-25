package com.cuentas.cuentas.mapper;

import com.cuentas.cuentas.dto.ClienteDTO;
import com.cuentas.cuentas.entity.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClienteMapper
{
    Cliente clienteDTOToCliente(final ClienteDTO clienteDTO);
}