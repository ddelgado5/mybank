package com.cuentas.cuentas.controller;

import com.cuentas.cuentas.dto.MovimientoDTO;
import com.cuentas.cuentas.service.MovimientoService;
import com.cuentas.cuentas.util.BadRequestException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/movimientos")
public class MovimientoController
{
    @Autowired
    private MovimientoService movimientoService;

    @GetMapping
    public ResponseEntity<List<MovimientoDTO>> findAll()
    {
        List<MovimientoDTO> clientes = movimientoService.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoDTO> findById(@PathVariable final Integer id)
    {
        return ResponseEntity.ok(movimientoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MovimientoDTO> create(@Valid @RequestBody final MovimientoDTO movimientoDTO, BindingResult bindingResult)
    {
        StringBuilder errors = new StringBuilder();
        if (bindingResult.hasErrors())
        {
            bindingResult.getAllErrors().forEach(objectError -> {
                errors.append(objectError.getDefaultMessage());
                errors.append("\n");
            });
            throw new BadRequestException(errors.toString());
        }
        MovimientoDTO newMovimientoDTO = movimientoService.create(movimientoDTO);
        log.info("Transacción creada con éxito");
        return ResponseEntity.status(HttpStatus.CREATED).body(newMovimientoDTO);
    }

    @PutMapping
    public ResponseEntity<MovimientoDTO> update(@RequestBody final MovimientoDTO movimientoDTO,  BindingResult bindingResult)
    {
        StringBuilder errors = new StringBuilder();
        if (bindingResult.hasErrors())
        {
            bindingResult.getAllErrors().forEach(objectError -> {
                errors.append(objectError.getDefaultMessage());
                errors.append("\n");
            });
            throw new BadRequestException(errors.toString());
        }
        MovimientoDTO movimientoDTOAcutalizado = movimientoService.update(movimientoDTO);
        log.info("Transacción actualizada con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(movimientoDTOAcutalizado);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id)
    {
        movimientoService.delete(id);
    }
}