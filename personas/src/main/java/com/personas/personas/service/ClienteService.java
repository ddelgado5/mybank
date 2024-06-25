package com.personas.personas.service;

import com.personas.personas.dto.ClienteDTO;
import com.personas.personas.entity.Cliente;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClienteService
{
    List<ClienteDTO> findAll();

    ClienteDTO findByClienteId(Integer clienteId);

    ClienteDTO create(ClienteDTO clienteDTO);

    ClienteDTO update(ClienteDTO clienteDTO);

    ClienteDTO patch(Integer clienteId, Map<String, Object> updates);

    boolean delete(Integer clienteId);

    Optional<ClienteDTO> findByIdentificacion(final String identificacion);
}