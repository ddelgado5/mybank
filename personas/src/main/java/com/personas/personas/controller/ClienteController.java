package com.personas.personas.controller;

import com.personas.personas.dto.ClienteDTO;
import com.personas.personas.service.ClienteService;
import com.personas.personas.util.BadRequestException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO>> findAll() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/{clienteId}")
    public ResponseEntity<ClienteDTO> findByClienteId(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(clienteService.findByClienteId(clienteId));
    }
    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<ClienteDTO> findByClienteId(@PathVariable String identificacion) {
        return ResponseEntity.ok(clienteService.findByIdentificacion(identificacion).get());
    }

    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO clienteDTO, BindingResult bindingResult) {
        validateBindingResult(bindingResult);
        ClienteDTO newclienteDTO = clienteService.create(clienteDTO);
        log.info("Cliente creado con éxito");
        return ResponseEntity.status(HttpStatus.CREATED).body(newclienteDTO);
    }

    @PutMapping
    public ResponseEntity<ClienteDTO> update(@Valid @RequestBody ClienteDTO clienteDTO, BindingResult bindingResult) {
        validateBindingResult(bindingResult);
        ClienteDTO clienteActualizado = clienteService.update(clienteDTO);
        log.info("Cliente actualizado con éxito");
        return ResponseEntity.ok(clienteActualizado);
    }

    @PatchMapping("/{clienteId}")
    public ResponseEntity<ClienteDTO> updatePatch(@PathVariable Integer clienteId, @RequestBody Map<String, Object> results) {
        ClienteDTO clienteActualizado = clienteService.patch(clienteId, results);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{clienteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer clienteId) {
        clienteService.delete(clienteId);
    }

    private void validateBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            bindingResult.getAllErrors().forEach(objectError -> {
                errors.append(objectError.getDefaultMessage()).append("\n");
            });
            throw new BadRequestException(errors.toString());
        }
    }
}