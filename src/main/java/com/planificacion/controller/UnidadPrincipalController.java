package com.planificacion.controller;

import com.planificacion.exception.ModeloNotFoundException;
import com.planificacion.model.UnidadPrincipal;
import com.planificacion.service.IUnidadPrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/unidadPrincipal")
public class UnidadPrincipalController {

    @Autowired
    private IUnidadPrincipalService service;

    @GetMapping
    public ResponseEntity<List<UnidadPrincipal>> listar() throws Exception {
        List<UnidadPrincipal> lista = service.listar();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadPrincipal> listarPorId(@PathVariable("id") Integer id) throws Exception {
        UnidadPrincipal obj = service.listarPorId(id);
        if (obj == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UnidadPrincipal> registrar(@Valid @RequestBody UnidadPrincipal p) throws Exception {
        UnidadPrincipal obj = service.registrar(p);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<UnidadPrincipal> modificar(@Valid @RequestBody UnidadPrincipal p) throws Exception {
        UnidadPrincipal obj = service.modificar(p);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        UnidadPrincipal obj = service.listarPorId(id);
        if (obj == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        service.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}