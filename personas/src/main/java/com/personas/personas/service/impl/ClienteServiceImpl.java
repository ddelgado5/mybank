package com.personas.personas.service.impl;

import com.personas.personas.dto.ClienteDTO;
import com.personas.personas.entity.Cliente;
import com.personas.personas.enums.GeneroType;
import com.personas.personas.mapper.ClienteMapper;
import com.personas.personas.repository.ClienteRepository;
import com.personas.personas.service.ClienteService;
import com.personas.personas.util.ConflictException;
import com.personas.personas.util.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@AllArgsConstructor
@Service
public class ClienteServiceImpl implements ClienteService
{
    private static final String CONTRASENA = "contrasena";
    private static final String GENERO = "genero";
    private static final String EDAD = "edad";
    private static final String IDENTIFICACION = "identificacion";

    private ClienteRepository clienteRepository;
    private ClienteMapper clienteMapper;

    @Override
    public List<ClienteDTO> findAll()
    {
        return  clienteRepository.findAll().stream().map(clienteMapper::clienteToClienteDTO).collect(Collectors.toList());
    }

    @Override
    public ClienteDTO findByClienteId(final Integer clienteId) {
        Optional<Cliente> cliente = clienteRepository.findByClienteId(clienteId);
        if (cliente.isPresent()) {
            return cliente.map(clienteMapper::clienteToClienteDTO).get();
        } else {
            log.error("Cliente no encontrado con el ID: {}", clienteId);
            throw new NotFoundException(String.format("No se pudo encontrar un cliente con el ID: %d", clienteId));
        }
    }

    @Override
    public ClienteDTO create(final ClienteDTO clienteDTO) {
        log.info("Inicio de la creación del cliente");
        if (getIdentificacion(clienteDTO.getIdentificacion()).isPresent()) {
            log.error("Ya existe un cliente con la identificación: {}", clienteDTO.getIdentificacion());
            throw new ConflictException(
                    String.format("Ya existe un cliente con la identificación: %s", clienteDTO.getIdentificacion()));
        }
        clienteDTO.setContrasena(encryptPassword(clienteDTO.getContrasena()));
        return clienteMapper.clienteToClienteDTO(
                clienteRepository.save(clienteMapper.clienteDTOToCliente(clienteDTO)));
    }

    @Override
    public ClienteDTO update(ClienteDTO clienteDTO) {
        log.info("Inicio de la actualización del cliente");
        Optional<Cliente> updateCliente = clienteRepository.findByClienteId(clienteDTO.getClienteId());
        if (updateCliente.isPresent()) {
            if (!clienteDTO.getIdentificacion().equals(updateCliente.get().getIdentificacion()) &&
                    getIdentificacion(clienteDTO.getIdentificacion()).isPresent()) {
                log.error("Ya existe un cliente con la identificación: {}", clienteDTO.getIdentificacion());
                throw new ConflictException(
                        String.format("Ya existe un cliente con la identificación: %s", clienteDTO.getIdentificacion()));
            }
            clienteDTO.setContrasena(encryptPassword(clienteDTO.getContrasena()));
            return clienteMapper.clienteToClienteDTO(
                    clienteRepository.save(clienteMapper.clienteDTOToCliente(clienteDTO)));
        } else {
            log.error("Cliente no encontrado con el ID: {}", clienteDTO.getClienteId());
            throw new NotFoundException(String.format("No se pudo encontrar un cliente con el ID: %d", clienteDTO.getClienteId()));
        }
    }

    @Override
    public ClienteDTO patch(Integer clienteId, Map<String, Object> updates) {
        Optional<Cliente> cliente = clienteRepository.findByClienteId(clienteId);
        if (cliente.isPresent()) {
            updates.forEach((key, value) -> {
                if (key.equals(IDENTIFICACION) && !value.equals(cliente.get().getIdentificacion()) && getIdentificacion(value.toString()).isPresent()) {
                    log.error("Ya existe un cliente con la identificación: {}", value.toString());
                    throw new ConflictException(String.format("Ya existe un cliente con la identificación: %s", value.toString()));
                }

                if (key.equals(EDAD)) {
                    value = Byte.parseByte(value.toString());
                }
                if (key.equals(GENERO)) {
                    value = GeneroType.valueOf(value.toString()).getValue();
                }
                if (key.equals(CONTRASENA)) {
                    value = encryptPassword(value.toString());
                }
                Field field = org.springframework.util.ReflectionUtils.findField(Cliente.class, key);
                field.setAccessible(true);
                ReflectionUtils.setField(field, cliente.get(), value);
            });
            return clienteMapper.clienteToClienteDTO(clienteRepository.save(cliente.get()));
        } else {
            log.error("Cliente no encontrado con el ID: {}", clienteId);
            throw new NotFoundException(String.format("No se pudo encontrar un cliente con el ID: %d", clienteId));
        }
    }

    @Override
    public boolean delete(Integer clienteId) {
        clienteRepository.findByClienteId(clienteId)
                .orElseThrow(() -> new NotFoundException(String.format("No se pudo encontrar un cliente con el ID: %d", clienteId)));
        clienteRepository.deleteByClienteId(clienteId);
        log.info("Cliente eliminado con éxito con el ID: {}", clienteId);
        return true;
    }

    @Override
    public Optional<ClienteDTO> findByIdentificacion(final String identificacion)
    {
        Optional<Cliente> cliente = clienteRepository.findByIdentificacion(identificacion);
        if (cliente.isPresent())
        {
            return Optional.of(clienteMapper.clienteToClienteDTO(cliente.get()));
        }
        else
        {
            throw new NotFoundException("Cliente no encontrado");
        }
    }

    public Optional<Cliente> getIdentificacion(String identificacion)
    {
        return clienteRepository.findByIdentificacion(identificacion);
    }

    private String encryptPassword(String password)
    {
        return String.valueOf(password.hashCode());
    }

}