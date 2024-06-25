package com.cuentas.cuentas.service.impl;

import com.cuentas.cuentas.entity.Cliente;
import com.cuentas.cuentas.enums.GeneroType;
import com.cuentas.cuentas.api.ClienteServiceApi;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ClienteServiceApiImplTest
{
	@Autowired
	private ClienteServiceApi clienteServiceApi;

	@MockBean
	private RestTemplate restTemplate;

	private Cliente getMockCliente(){
		return Cliente.builder()
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
	void whenGetClienteById_thenClienteShouldBeReturned() {
		Cliente clienteEsperado = getMockCliente();
		ResponseEntity<Cliente> responseEntity = ResponseEntity.ok(clienteEsperado);
		Mockito.when(restTemplate.getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.eq(Cliente.class))).thenReturn(responseEntity);

		Cliente cliente = clienteServiceApi.getClienteById(1);

		assertNotNull(cliente);
		assertEquals(clienteEsperado.getClienteId(), cliente.getClienteId());
		assertEquals(clienteEsperado.getNombre(), cliente.getNombre());
		assertEquals(clienteEsperado.getIdentificacion(), cliente.getIdentificacion());
		assertEquals(clienteEsperado.getTelefono(), cliente.getTelefono());
		assertEquals(clienteEsperado.getDireccion(), cliente.getDireccion());
		assertEquals(clienteEsperado.getGenero(), cliente.getGenero());
		assertEquals(clienteEsperado.getEdad(), cliente.getEdad());
		assertEquals(clienteEsperado.getEstado(), cliente.getEstado());
		assertEquals(clienteEsperado.getContrasena(), cliente.getContrasena());

		Mockito.verify(restTemplate).getForEntity(ArgumentMatchers.anyString(), ArgumentMatchers.eq(Cliente.class));
	}
}