 package com.personas.personas.mapper;

 import com.personas.personas.dto.ClienteDTO;
 import com.personas.personas.entity.Cliente;
 import org.mapstruct.Mapper;

 @Mapper(componentModel = "spring")
 public interface ClienteMapper
 {
     ClienteDTO clienteToClienteDTO(final Cliente cliente);
     Cliente clienteDTOToCliente(final ClienteDTO clienteDTO);
 }