package com.planificacion.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.planificacion.exception.ModeloNotFoundException;
import com.planificacion.model.Product;
import com.planificacion.service.IProductService;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService service;

    @GetMapping
    @PreAuthorize("@authServiceImpl.tieneAcceso('listar')")
    public ResponseEntity<List<Product>> listar() throws Exception {
        List<Product> lista = service.listar();
        return new ResponseEntity<List<Product>>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> listarPorId(@PathVariable("id") Integer id) throws Exception {
        Product obj = service.listarPorId(id);

        if (obj == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        return new ResponseEntity<Product>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> registrar(@Valid @RequestBody Product p) throws Exception {
        Product obj = service.registrar(p);

        // ✅ La respuesta ahora incluye el objeto 'obj' en el cuerpo
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Product> modificar(@Valid @RequestBody Product p) throws Exception {
        Product obj = service.modificar(p);
        return new ResponseEntity<Product>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        Product obj = service.listarPorId(id);

        if (obj == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }

        service.eliminar(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}