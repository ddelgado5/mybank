package com.cuentas.cuentas.controller;

import com.cuentas.cuentas.dto.CuentaDTO;
import com.cuentas.cuentas.service.CuentaService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/cuentas")
public class CuentaController
{
    @Autowired
    private CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> findAll()
    {
        List<CuentaDTO> cuentas = cuentaService.findAll();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> findById(@PathVariable final Integer id)
    {
        return ResponseEntity.ok(cuentaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CuentaDTO> create(@Valid @RequestBody final CuentaDTO cuentaDTO, BindingResult bindingResult)
            throws BadRequestException
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
        CuentaDTO newcuentaDTO = cuentaService.create(cuentaDTO);
        log.info("Cuenta creada con éxito");
        return ResponseEntity.status(HttpStatus.CREATED).body(newcuentaDTO);

    }

    @PutMapping
    public ResponseEntity<CuentaDTO> update(@RequestBody final CuentaDTO cuentaDTO, BindingResult bindingResult)
            throws BadRequestException
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
        CuentaDTO newcuentaActualizada = cuentaService.update(cuentaDTO);
        log.info("Cuenta actualizada con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(newcuentaActualizada);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<CuentaDTO> patch(@PathVariable final Integer id, @RequestBody final Map<String, Object> result)
    {
        CuentaDTO cuentaActualizada = cuentaService.updatePatch(id, result);
        log.info("Cuenta actualizada con éxito");
        return ResponseEntity.status(HttpStatus.OK).body(cuentaActualizada);

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id)
    {
        cuentaService.delete(id);
    }
}
