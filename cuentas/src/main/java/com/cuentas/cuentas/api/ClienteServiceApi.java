package com.cuentas.cuentas.api;

import com.cuentas.cuentas.dto.ClienteDTO;
import com.cuentas.cuentas.entity.Cliente;
public interface  ClienteServiceApi {

    Cliente getClienteById(Integer clienteId);
    Cliente getClienteByIdentificacion(String identificacion);
}
