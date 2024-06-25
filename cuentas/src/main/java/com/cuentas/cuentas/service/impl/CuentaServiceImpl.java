package com.cuentas.cuentas.service.impl;

import com.cuentas.cuentas.dto.CuentaDTO;
import com.cuentas.cuentas.entity.Cliente;
import com.cuentas.cuentas.entity.Cuenta;
import com.cuentas.cuentas.enums.CuentaType;
import com.cuentas.cuentas.mapper.CuentaMapper;
import com.cuentas.cuentas.repository.CuentaRepository;
import com.cuentas.cuentas.api.ClienteServiceApi;
import com.cuentas.cuentas.service.CuentaService;
import com.cuentas.cuentas.util.ConflictException;
import com.cuentas.cuentas.util.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class CuentaServiceImpl implements CuentaService
{
    public static final String TIPO_CUENTA = "tipoCuenta";
    public static final String NUMERO_CUENTA = "numeroCuenta";

    private final CuentaRepository cuentaRepository;
    private final ClienteServiceApi clienteServiceApi;
    private final CuentaMapper cuentaMapper;


    @Override
    public List<CuentaDTO> findAll()
    {
        return cuentaRepository.findAll().stream().map(cuentaMapper::cuentaToCuentaDTO).collect(Collectors.toList());
    }

    @Override
    public CuentaDTO findById(final Integer id)
    {
        Optional<Cuenta> cuenta = cuentaRepository.findById(id);
        if (cuenta.isPresent())
        {
            return cuenta.map(cuentaMapper::cuentaToCuentaDTO).get();
        }
        else
        {
            log.error("Cuenta no encontrada por ID: {}", id);
            throw new NotFoundException(String.format("No se encontró la cuenta con el ID: %d", id));
        }
    }

    @Transactional
    @Override
    public CuentaDTO create(final CuentaDTO cuentaDTO)
    {
        log.info("Inicio de la creación de la cuenta");
        Cliente cliente = clienteServiceApi.getClienteByIdentificacion(cuentaDTO.getCliente().getIdentificacion());

        if (findByNumeroCuenta(cuentaDTO.getNumeroCuenta()).isPresent())
        {
            log.error("Ya existe una cuenta con el número: {}", cuentaDTO.getNumeroCuenta());
            throw new ConflictException(
                    String.format("Ya existe una cuenta con el número: %s", cuentaDTO.getNumeroCuenta()));
        }
        Cuenta cuenta = cuentaMapper.cuentaDTOToCuenta(cuentaDTO);
        cuenta.setCliente(cliente);
        return cuentaMapper.cuentaToCuentaDTO(cuentaRepository.save(cuenta));
    }

    @Transactional
    @Override
    public CuentaDTO updatePatch(final Integer id, final Map<String, Object> results)
    {
        log.info("Inicio de la actualización parcial de la cuenta");
        Optional<Cuenta> cuenta = cuentaRepository.findById(id);
        if (cuenta.isPresent())
        {

            results.forEach((k, v) -> {
                if (k.equals(NUMERO_CUENTA) && !v.equals(cuenta.get().getNumeroCuenta()) && findByNumeroCuenta(
                        v.toString()).isPresent())
                {
                    log.error("Ya existe una cuenta con el número: {}", v.toString());
                    throw new ConflictException(String.format("Ya existe una cuenta con el número: %d", v.toString()));
                }

                if (k.equals(TIPO_CUENTA))
                {
                    v = CuentaType.valueOf(v.toString());
                }

                Field field = ReflectionUtils.findField(Cuenta.class, k);
                field.setAccessible(true);
                ReflectionUtils.setField(field, cuenta.get(), v);
            });
            return cuentaMapper.cuentaToCuentaDTO(cuentaRepository.save(cuenta.get()));
        }
        else
        {
            log.error("Cuenta no encontrada por ID: {}", id);
            throw new NotFoundException(String.format("No se encontró la cuenta con el ID: %d", id));
        }
    }

    @Transactional
    @Override
    public CuentaDTO update(final CuentaDTO cuentaDTO)
    {
        log.info("Cuenta actualizada con éxito");
        Optional<Cuenta> updateCuenta = cuentaRepository.findById(cuentaDTO.getId());
        if (updateCuenta.isPresent())
        {
            if (!cuentaDTO.getNumeroCuenta().equals(updateCuenta.get().getNumeroCuenta()) && Objects.nonNull(
                    findByNumeroCuenta(cuentaDTO.getNumeroCuenta())))
            {
                log.error("Ya existe una cuenta con el número: {}", cuentaDTO.getNumeroCuenta());
                throw new ConflictException(
                        String.format("Ya existe una cuenta con el número: %s", cuentaDTO.getNumeroCuenta()));
            }
            Cliente cliente = clienteServiceApi.getClienteByIdentificacion(cuentaDTO.getCliente().getIdentificacion());

            final Cuenta cuenta = cuentaMapper.cuentaDTOToCuenta(cuentaDTO);
            cuenta.setCliente(cliente);
            return cuentaMapper.cuentaToCuentaDTO(cuentaRepository.save(cuenta));
        }
        else
        {
            log.error("Cuenta no encontrada por ID: {}", cuentaDTO.getId());
            throw new NotFoundException(String.format("No se encontró la cuenta con el ID: %d", cuentaDTO.getId()));
        }
    }

    @Override
    public void delete(final Integer id)
    {
        cuentaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Cuenta no encontrada por ID: %d", id)));
        cuentaRepository.deleteById(id);
        log.info("Cuenta eliminada con éxito con el ID: {}", id);
    }

    @Override
    public Optional<Cuenta> findByNumeroCuenta(String numeroCuenta)
    {
        return cuentaRepository.findByNumeroCuenta(numeroCuenta);
    }

    @Override
    public List<Cuenta> findAllByCliente(Cliente cliente)
    {
        return cuentaRepository.findAllByCliente(cliente);
    }
}