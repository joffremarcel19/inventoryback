package com.planificacion.service.impl;

import com.planificacion.model.InventoryUnit;
import com.planificacion.model.Movement;
import com.planificacion.repo.IInventoryUnitRepo;
import com.planificacion.repo.IMovementRepo;
import com.planificacion.service.IMovementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovementServiceImpl implements IMovementService {

    private static final Logger logger = LoggerFactory.getLogger(MovementServiceImpl.class);

    @Autowired
    private IMovementRepo movementRepo;

    @Autowired
    private IInventoryUnitRepo inventoryUnitRepo;

    @Transactional
    @Override
    public Movement register(Movement movement) {
        logger.info("Registering movement: {}", movement);

        if (movement.getType() == null || movement.getType().isEmpty()) {
            logger.error("El tipo de movimiento es obligatorio.");
            throw new IllegalArgumentException("El tipo de movimiento es obligatorio.");
        }

        // Validar existencia de unidad de inventario
        InventoryUnit inventoryUnit = inventoryUnitRepo.findById(movement.getInventoryUnit().getId()).orElse(null);
        if (inventoryUnit == null) {
            logger.error("Unidad de inventario no encontrada con ID: {}", movement.getInventoryUnit().getId());
            throw new IllegalArgumentException("Unidad de inventario no encontrada.");
        }
        logger.info("Found InventoryUnit: {}", inventoryUnit);

        int cantidad = movement.getQuantity();
        logger.info("Movement quantity: {}", cantidad);
        logger.info("Movement type: {}", movement.getType());

        if (movement.getType().equalsIgnoreCase("ENTRADA")) {
            if (cantidad <= 0) {
                logger.error("La cantidad para una entrada debe ser mayor a 0.");
                throw new IllegalArgumentException("La cantidad para una entrada debe ser mayor a 0.");
            }
            inventoryUnit.setStock(inventoryUnit.getStock() + cantidad);
            logger.info("Stock incremented to: {}", inventoryUnit.getStock());

        } else if (movement.getType().equalsIgnoreCase("SALIDA")) {
            if (cantidad < 0) {
                logger.error("No se puede entregar una cantidad negativa.");
                throw new IllegalArgumentException("No se puede entregar una cantidad negativa.");
            }
            if (cantidad == 0) {
                logger.error("No se puede entregar 0 unidades.");
                throw new IllegalArgumentException("No se puede entregar 0 unidades.");
            }
            if (inventoryUnit.getStock() < cantidad) {
                logger.error("Stock insuficiente. Stock actual: {}, Cantidad solicitada: {}", inventoryUnit.getStock(), cantidad);
                throw new IllegalArgumentException("Stock insuficiente. Stock actual: " + inventoryUnit.getStock());
            }
            inventoryUnit.setStock(inventoryUnit.getStock() - cantidad);
            logger.info("Stock decremented to: {}", inventoryUnit.getStock());

        } else {
            logger.error("Tipo de movimiento no válido: {}", movement.getType());
            throw new IllegalArgumentException("Tipo de movimiento no válido. Use 'ENTRADA' o 'SALIDA'.");
        }

        // Guardar el movimiento
        movement.setMovementDate(LocalDateTime.now());
        Movement savedMovement = movementRepo.save(movement);
        logger.info("Movement registered: {}", savedMovement);

        // Guardar el inventario actualizado
        InventoryUnit updatedInventoryUnit = inventoryUnitRepo.save(inventoryUnit);
        logger.info("InventoryUnit updated: {}", updatedInventoryUnit);

        return savedMovement;
    }

    @Override
    public List<Movement> listAll() {
        return movementRepo.findAll();
    }
}