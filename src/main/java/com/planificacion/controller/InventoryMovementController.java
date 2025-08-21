package com.planificacion.controller;

import com.planificacion.dto.InventoryMovementDTO;
import com.planificacion.dto.InventoryMovementRequestDTO;
import com.planificacion.exception.ModeloNotFoundException;
import com.planificacion.model.InventoryMovement;
import com.planificacion.service.IInventoryMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inventory-movements")
public class InventoryMovementController {

    @Autowired
    private IInventoryMovementService inventoryMovementService;

    @PostMapping
    public ResponseEntity<InventoryMovementDTO> registerMovement(@Valid @RequestBody InventoryMovementRequestDTO request) throws Exception {
        InventoryMovement newMovement = inventoryMovementService.register(request);
        InventoryMovementDTO responseDTO = new InventoryMovementDTO(newMovement);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryMovementDTO> updateMovement(@PathVariable Integer id, @Valid @RequestBody InventoryMovementRequestDTO request) throws Exception {
        InventoryMovement updatedMovement = inventoryMovementService.update(id, request);
        InventoryMovementDTO responseDTO = new InventoryMovementDTO(updatedMovement);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    
    @GetMapping
    public ResponseEntity<List<InventoryMovementDTO>> getAllMovements() throws Exception {
        List<InventoryMovement> movements = inventoryMovementService.listAll();
        List<InventoryMovementDTO> responseDTOs = movements.stream()
                .map(InventoryMovementDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(responseDTOs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovementDTO> getMovementById(@PathVariable Integer id) throws Exception {
        InventoryMovement movement = inventoryMovementService.listById(id);
        if (movement == null) {
            throw new ModeloNotFoundException("Inventory movement not found with ID: " + id);
        }
        InventoryMovementDTO responseDTO = new InventoryMovementDTO(movement);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovement(@PathVariable Integer id) throws Exception {
        inventoryMovementService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}