package com.planificacion.controller;

import com.planificacion.dto.DeliveryRequestDTO;
import com.planificacion.model.Delivery;
import com.planificacion.service.IDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.planificacion.dto.DeliveryRequestDTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private IDeliveryService service;

    // Endpoint para registrar múltiples entregas (usando DeliveryRequestDTO directamente)
    @PostMapping
    public ResponseEntity<?> registerMultiple(@RequestBody DeliveryRequestDTO request) { 
        // Validaciones básicas que puedes hacer en el controlador antes de pasar al servicio
        if (request.getItems() == null || request.getItems().isEmpty()) {
            return new ResponseEntity<>("Error en la petición de entrega: la lista de items está vacía o es nula.", HttpStatus.BAD_REQUEST);
        }
        
        // Validación para deliveryTypeDetailId
        if (request.getDeliveryTypeDetailId() == null || request.getDeliveryTypeDetailId() <= 0) {
            return new ResponseEntity<>("Debe proporcionar un 'deliveryTypeDetailId' válido para el tipo de entrega.", HttpStatus.BAD_REQUEST);
        }
        
        // Validación para personId
        if (request.getPersonId() <= 0) {
            return new ResponseEntity<>("Debe proporcionar un 'personId' válido para la persona que recibe.", HttpStatus.BAD_REQUEST);
        }

        // Validación para userId
        if (request.getUserId() <= 0) {
            return new ResponseEntity<>("Debe proporcionar un 'userId' válido para el usuario que realiza la entrega.", HttpStatus.BAD_REQUEST);
        }

        try {
            // Llama al servicio para registrar la entrega, el servicio se encarga del mapeo a entidades
            Delivery savedDelivery = service.registerMultipleDeliveries(request);
            
            // Construye la URI para la respuesta 201 Created
            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                    .buildAndExpand(savedDelivery.getId()).toUri();
            
            // Devuelve la respuesta 201 Created con la ubicación y el objeto guardado
            return ResponseEntity.created(location).body(savedDelivery);

        } catch (RuntimeException ex) {
            // Captura las RuntimeException (ej. "Persona no encontrada", "Stock insuficiente")
            // lanzadas por el servicio y devuelve un 400 Bad Request con el mensaje de la excepción.
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Captura cualquier otra excepción inesperada y devuelve un 500 Internal Server Error.
            e.printStackTrace(); // Para depuración, considera usar un logger.
            return new ResponseEntity<>("Error interno del servidor al procesar la entrega: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para obtener una entrega por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable("id") Integer id) {
        Delivery delivery = service.findById(id);
        if (delivery != null) {
            return new ResponseEntity<>(delivery, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para listar todas las entregas
    @GetMapping
    public ResponseEntity<List<Delivery>> list() {
        List<Delivery> deliveries = service.listAll();
        return new ResponseEntity<>(deliveries, HttpStatus.OK);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}