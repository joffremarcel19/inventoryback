package com.planificacion.service;

import java.util.List;

import com.planificacion.dto.InventoryMovementRequestDTO;
import com.planificacion.model.Delivery;
import com.planificacion.model.InventoryMovement;
import com.planificacion.model.Purchase;

public interface IInventoryMovementService {
    InventoryMovement register(InventoryMovementRequestDTO request) throws Exception;
    InventoryMovement update(Integer id, InventoryMovementRequestDTO request) throws Exception;
    List<InventoryMovement> listAll() throws Exception;
    InventoryMovement listById(Integer id) throws Exception;
    void delete(Integer id) throws Exception;
    void createMovementFromPurchase(Purchase purchase) throws Exception;
    // ✅ Nuevo método para registrar movimientos a partir de una entrega
    void createMovementFromDelivery(Delivery delivery) throws Exception;
}