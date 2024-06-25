package com.personas.personas.service.impl;

import com.personas.personas.dto.ClienteDTO;
import com.personas.personas.entity.Cliente;
import com.personas.personas.enums.GeneroType;
import com.personas.personas.mapper.ClienteMapper;
import com.personas.personas.repository.ClienteRepository;
import com.personas.personas.util.ConflictException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest
{
	@Mock
	private ClienteRepository clienteRepository;
	@Mock
	private ClienteMapper clienteMapper;

	@InjectMocks
	private ClienteServiceImpl clienteService;

	private Cliente getMockCliente(){
		return Cliente.builder()
				.nombre("Jose Lema")
				.clienteId(1)
				.identificacion("1234567890")
				.telefono("098254785")
				.direccion("Itagui transversal 48")
				.genero(GeneroType.MASCULINO.getValue())
				.edad((byte) 20)
				.estado(true)
				.contrasena("123456")
				.build();
	}

	private ClienteDTO getMockClienteDTO(){
		return ClienteDTO.builder()
				.nombre("Jose Lema")
				.clienteId(1)
				.identificacion("123456789")
				.telefono("098254785")
				.direccion("Itagui transversal 48")
				.genero(GeneroType.MASCULINO)
				.edad((byte) 20)
				.estado(true)
				.contrasena("123456")
				.build();
	}

	@Test
	void whenCreateCalledWithNewCliente_thenClienteShouldBeCreated() {
		ClienteDTO clienteDTO = getMockClienteDTO();
		Cliente cliente = getMockCliente();

		Mockito.when(clienteRepository.findByIdentificacion(ArgumentMatchers.anyString())).thenReturn(Optional.empty());

		Mockito.when(clienteRepository.save(ArgumentMatchers.any(Cliente.class))).thenReturn(cliente);
		Mockito.when(clienteMapper.clienteToClienteDTO(ArgumentMatchers.any(Cliente.class))).thenReturn(clienteDTO);
		Mockito.when(clienteMapper.clienteDTOToCliente(ArgumentMatchers.any(ClienteDTO.class))).thenReturn(cliente);

		ClienteDTO result = clienteService.create(clienteDTO);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(clienteDTO.getClienteId(), result.getClienteId());
		Assertions.assertEquals(clienteDTO.getNombre(), result.getNombre());
		Assertions.assertEquals(clienteDTO.getIdentificacion(), result.getIdentificacion());
		Assertions.assertEquals(clienteDTO.getTelefono(), result.getTelefono());
		Assertions.assertEquals(clienteDTO.getDireccion(), result.getDireccion());
		Assertions.assertEquals(clienteDTO.getGenero(), result.getGenero());
		Assertions.assertEquals(clienteDTO.getEdad(), result.getEdad());
		Assertions.assertEquals(clienteDTO.getEstado(), result.getEstado());
		Assertions.assertEquals(clienteDTO.getContrasena(), result.getContrasena());

		Mockito.verify(clienteRepository).findByIdentificacion(ArgumentMatchers.anyString());
		Mockito.verify(clienteRepository).save(ArgumentMatchers.any(Cliente.class));
	}

	@Test
	void whenCreateCalledWithExistingCliente_thenConflictExceptionShouldBeThrown() {
		ClienteDTO clienteDTO = getMockClienteDTO();
		Cliente cliente = getMockCliente();

		Mockito.when(clienteRepository.findByIdentificacion(ArgumentMatchers.anyString())).thenReturn(Optional.of(cliente));

		Assertions.assertThrows(ConflictException.class, () -> clienteService.create(clienteDTO));

		Mockito.verify(clienteRepository, Mockito.never()).save(ArgumentMatchers.any(Cliente.class));
	}

}