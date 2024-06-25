package com.cuentas.cuentas.service.impl;

import com.cuentas.cuentas.dto.ClienteDTO;
import com.cuentas.cuentas.dto.CuentaDTO;
import com.cuentas.cuentas.entity.Cliente;
import com.cuentas.cuentas.entity.Cuenta;
import com.cuentas.cuentas.enums.CuentaType;
import com.cuentas.cuentas.enums.GeneroType;
import com.cuentas.cuentas.mapper.CuentaMapper;
import com.cuentas.cuentas.repository.CuentaRepository;
import com.cuentas.cuentas.api.ClienteServiceApi;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CuentaServiceImplTest
{
	@Mock
	private ClienteServiceApi clienteServiceApi;
	@Mock
	private CuentaMapper cuentaMapper;
	@Mock
	private CuentaRepository cuentaRepository;
	@InjectMocks
	CuentaServiceImpl cuentaService;

	private CuentaDTO getMockCuentaDto(){
		return CuentaDTO.builder()
				.tipoCuenta(CuentaType.AHORROS)
				.id(1)
				.numeroCuenta("478758")
				.saldoInicial(500.0)
				.estado(true)
				.cliente(getMockClienteDTO())
				.build();
	}

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

	private ClienteDTO getMockClienteDTO(){
		return ClienteDTO.builder()
				.nombre("Jose Lema")
				.clienteId(1)
				.identificacion("123456789")
				.telefono("098254785")
				.direccion("Itagui transversal 48")
				.genero("Masculino")
				.edad((byte) 20)
				.estado(true)
				.contrasena("123456")
				.build();
	}

	private Cuenta getMockCuenta(){
		return Cuenta.builder()
				.tipoCuenta(CuentaType.AHORROS)
				.id(1)
				.numeroCuenta("478758")
				.saldoInicial(500.0)
				.estado(true)
				.cliente(getMockCliente())
				.build();
	}



	@Test
	public void givenCuentaDtoWhenCreateThenExpectCuentaDto()
	{
		CuentaDTO cuentaDTO = getMockCuentaDto();
		Cliente cliente = getMockCliente();
		Cuenta cuenta = getMockCuenta();

		Mockito.when(clienteServiceApi.getClienteByIdentificacion(ArgumentMatchers.anyString())).thenReturn(cliente);
		Mockito.when(cuentaRepository.save(ArgumentMatchers.any(Cuenta.class))).thenReturn(cuenta);
		Mockito.when(cuentaMapper.cuentaDTOToCuenta(ArgumentMatchers.any(CuentaDTO.class))).thenReturn(cuenta);
		Mockito.when(cuentaMapper.cuentaToCuentaDTO(ArgumentMatchers.any(Cuenta.class))).thenReturn(cuentaDTO);

		CuentaDTO result = cuentaService.create(cuentaDTO);
		Assertions.assertNotNull(result);
		Assertions.assertNotNull(result);
		Assertions.assertEquals(cuentaDTO.getTipoCuenta(), result.getTipoCuenta());
		Assertions.assertEquals(cuentaDTO.getId(), result.getId());
		Assertions.assertEquals(cuentaDTO.getNumeroCuenta(), result.getNumeroCuenta());
		Assertions.assertEquals(cuentaDTO.getSaldoInicial(), result.getSaldoInicial());
		Assertions.assertEquals(cuentaDTO.getEstado(), result.getEstado());


		Mockito.verify(clienteServiceApi).getClienteByIdentificacion(ArgumentMatchers.anyString());
		Mockito.verify(cuentaRepository).save(ArgumentMatchers.any(Cuenta.class));
		Mockito.verify(cuentaMapper).cuentaDTOToCuenta(ArgumentMatchers.any(CuentaDTO.class));
		Mockito.verify(cuentaMapper).cuentaToCuentaDTO(ArgumentMatchers.any(Cuenta.class));
	}
}