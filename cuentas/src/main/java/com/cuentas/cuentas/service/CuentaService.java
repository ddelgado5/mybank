package com.cuentas.cuentas.service;

import com.cuentas.cuentas.dto.CuentaDTO;
import com.cuentas.cuentas.entity.Cliente;
import com.cuentas.cuentas.entity.Cuenta;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CuentaService
{
    List<CuentaDTO> findAll();

    CuentaDTO findById(final Integer id);

    CuentaDTO create(final CuentaDTO cuentaDTO);

    CuentaDTO update(final CuentaDTO cuentaDTO);

    CuentaDTO updatePatch(final Integer id, final Map<String, Object> results);

    void delete(final Integer id);

    Optional<Cuenta> findByNumeroCuenta(final String numeroCuenta);

    List<Cuenta> findAllByCliente(Cliente cliente);
}