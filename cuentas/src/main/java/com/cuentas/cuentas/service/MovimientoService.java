package com.cuentas.cuentas.service;

import com.cuentas.cuentas.dto.MovimientoDTO;

import java.util.List;

public interface MovimientoService
{
    List<MovimientoDTO> findAll();

    MovimientoDTO findById(final Integer id);

    MovimientoDTO create(final MovimientoDTO movimientoDTO);

    MovimientoDTO update(final MovimientoDTO movimientoDTO);

    void delete(final Integer id);
}