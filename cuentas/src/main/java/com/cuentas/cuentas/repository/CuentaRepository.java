package com.cuentas.cuentas.repository;

import com.cuentas.cuentas.entity.Cliente;
import com.cuentas.cuentas.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Integer>
{
    Optional<Cuenta> findByNumeroCuenta(final String numeroCuenta);

    List<Cuenta> findAllByCliente(Cliente cliente);
}