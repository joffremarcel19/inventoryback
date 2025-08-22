package com.planificacion.controller;

import com.planificacion.exception.ModeloNotFoundException;
import com.planificacion.model.InventoryUnit;
import com.planificacion.service.IInventoryUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException; // Importa la excepción
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/inventoryunit")
public class InventoryUnitController {

    @Autowired
    private IInventoryUnitService service;

    // Buscar unidades de inventario por nombre de producto
    @GetMapping("/search")
    public ResponseEntity<List<InventoryUnit>> buscarPorNombreProducto(@RequestParam("name") String name) throws Exception {
        List<InventoryUnit> resultados = service.buscarPorNombreProducto(name);
        if (resultados.isEmpty()) {
            throw new ModeloNotFoundException("No se encontraron productos con nombre: " + name);
        }
        return new ResponseEntity<>(resultados, HttpStatus.OK);
    }

    // Registrar nueva unidad de inventario

    @PostMapping
    public ResponseEntity<InventoryUnit> registrar(@Valid @RequestBody InventoryUnit i) throws Exception {
        InventoryUnit obj = service.registrar(i);
        return new ResponseEntity<>(obj, HttpStatus.CREATED);
    }

    // Modificar unidad de inventario
    @PutMapping
    public ResponseEntity<InventoryUnit> modificar(@Valid @RequestBody InventoryUnit i) throws Exception {
        InventoryUnit obj = service.modificar(i);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    // Eliminar unidades de inventario por nombre
    @DeleteMapping
    public ResponseEntity<Void> eliminarPorNombre(@RequestParam("name") String name) throws Exception {
        List<InventoryUnit> unidades = service.buscarPorNombreProducto(name);
        if (unidades.isEmpty()) {
            throw new ModeloNotFoundException("No se encontraron productos con nombre: " + name);
        }
        for (InventoryUnit u : unidades) {
            service.eliminar(u.getId());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Listar todas las unidades de inventario
    @GetMapping
    public ResponseEntity<List<InventoryUnit>> listarTodos() throws Exception {
        List<InventoryUnit> lista = service.listar();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Buscar unidades de inventario por ID de Unidad Principal (nueva ruta)
 // ✅ Modifica la ruta para evitar el conflicto
    @GetMapping("/by-principal-unit/{idUnidadP}")
    public ResponseEntity<List<InventoryUnit>> listarPorUnidadPrincipal(@PathVariable("idUnidadP") Integer idUnidadP) throws Exception {
        List<InventoryUnit> lista = service.listarPorUnidadPrincipal(idUnidadP);
        if (lista.isEmpty()) {
            throw new ModeloNotFoundException("No se encontraron unidades de inventario para la Unidad Principal con ID: " + idUnidadP);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    // Manejador de excepción para DuplicateKeyException
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<String> handleDuplicateKeyException(DuplicateKeyException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
    
 // ✅ Nuevo endpoint para aumentar el stock
    @PostMapping("/addstock")
    public ResponseEntity<InventoryUnit> aumentarStock(
            @RequestParam("productId") Integer productId,
            @RequestParam("unidadAdministrativaId") Integer unidadAdministrativaId,
            @RequestParam("cantidad") Integer cantidad) throws Exception {

        InventoryUnit updatedUnit = service.aumentarStock(productId, unidadAdministrativaId, cantidad);
        return new ResponseEntity<>(updatedUnit, HttpStatus.OK);
    }
 // ✅ Nuevo endpoint para listar por ID de Unidad Administrativa
    @GetMapping("/by-administrative-unit/{idUniAdm}")
    public ResponseEntity<List<InventoryUnit>> listarPorUnidadAdministrativa(@PathVariable("idUniAdm") Integer idUniAdm) throws Exception {
        List<InventoryUnit> lista = service.listarPorUnidadAdministrativa(idUniAdm);
        if (lista.isEmpty()) {
            throw new ModeloNotFoundException("No se encontraron productos para la Unidad Administrativa con ID: " + idUniAdm);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}