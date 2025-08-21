package com.planificacion.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planificacion.dto.InventoryMovementRequestDTO;
import com.planificacion.dto.InventoryMovementRequestDTO.InventoryMovementDetailRequestDTO;
import com.planificacion.exception.ModeloNotFoundException;
import com.planificacion.model.Delivery;
import com.planificacion.model.DeliveryDetail;
import com.planificacion.model.InventoryMovement;
import com.planificacion.model.InventoryMovementDetail;
import com.planificacion.model.InventoryUnit;
import com.planificacion.model.Product;
import com.planificacion.model.Purchase;
import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.model.Usuario;
import com.planificacion.repo.IInventoryMovementRepo;
import com.planificacion.repo.IProductRepo;
import com.planificacion.repo.IUnidadAdministrativaRepo;
import com.planificacion.repo.IUsuarioRepo;
import com.planificacion.service.IInventoryMovementService;
import com.planificacion.model.PurchaseDetail;
import com.planificacion.service.IInventoryUnitService;

@Service
public class InventoryMovementServiceImpl implements IInventoryMovementService {

    @Autowired
    private IInventoryMovementRepo inventoryMovementRepo;

    @Autowired
    private IProductRepo productRepo;

    @Autowired
    private IUsuarioRepo usuarioRepo;

    @Autowired
    private IUnidadAdministrativaRepo unidadAdministrativaRepo;
    @Autowired
    private IInventoryUnitService inventoryUnitService;
    

    @Transactional
    @Override
    public InventoryMovement register(InventoryMovementRequestDTO request) throws Exception {
        
        // 1. Validar y mapear los datos del encabezado
        InventoryMovement movement = new InventoryMovement();
        movement.setMovementType(request.getMovementType());
        movement.setMovementDate(LocalDateTime.now());
        movement.setIdMov(request.getIdMov());
        movement.setDocumentType(request.getDocumentType());

        Usuario user = usuarioRepo.findById(request.getUserId())
                .orElseThrow(() -> new ModeloNotFoundException("User not found with ID: " + request.getUserId()));
        movement.setUser(user);

        UnidadAdministrativa administrativeUnit = unidadAdministrativaRepo.findById(request.getAdministrativeUnitId())
                .orElseThrow(() -> new ModeloNotFoundException("Administrative Unit not found with ID: " + request.getAdministrativeUnitId()));
        movement.setAdministrativeUnit(administrativeUnit);

        movement.setDetails(new ArrayList<>());
        
        // 2. Procesar y mapear los detalles
        for (InventoryMovementDetailRequestDTO detailDTO : request.getDetails()) {
            Product product = productRepo.findById(detailDTO.getProductId())
                    .orElseThrow(() -> new ModeloNotFoundException("Product not found with ID: " + detailDTO.getProductId()));
            
            // 3. Crear el detalle del movimiento
            InventoryMovementDetail detail = new InventoryMovementDetail();
            detail.setProduct(product);
            detail.setQuantity(detailDTO.getQuantity());
            
            movement.addDetail(detail);
        }

        // 4. Guardar el movimiento completo
        return inventoryMovementRepo.save(movement);
    }
    
    @Override
    public List<InventoryMovement> listAll() throws Exception {
        return inventoryMovementRepo.findAll();
    }

    @Override
    public InventoryMovement listById(Integer id) throws Exception {
        return inventoryMovementRepo.findById(id)
                .orElseThrow(() -> new ModeloNotFoundException("Inventory movement not found with ID: " + id));
    }

    @Transactional
    @Override
    public InventoryMovement update(Integer id, InventoryMovementRequestDTO request) throws Exception {
        InventoryMovement existingMovement = inventoryMovementRepo.findById(id)
                .orElseThrow(() -> new ModeloNotFoundException("Inventory movement not found with ID: " + id));

        // Actualizar campos principales
        existingMovement.setMovementType(request.getMovementType());
        existingMovement.setIdMov(request.getIdMov());
        existingMovement.setDocumentType(request.getDocumentType());

        // Actualizar relaciones (opcional, si los IDs cambian)
        existingMovement.setUser(usuarioRepo.findById(request.getUserId())
                .orElseThrow(() -> new ModeloNotFoundException("User not found with ID: " + request.getUserId())));
        existingMovement.setAdministrativeUnit(unidadAdministrativaRepo.findById(request.getAdministrativeUnitId())
                .orElseThrow(() -> new ModeloNotFoundException("Administrative Unit not found with ID: " + request.getAdministrativeUnitId())));

        // Borrar los detalles antiguos y agregar los nuevos.
        // `orphanRemoval=true` se encarga de eliminar los detalles viejos.
        existingMovement.getDetails().clear();
        for (InventoryMovementDetailRequestDTO detailDTO : request.getDetails()) {
            Product product = productRepo.findById(detailDTO.getProductId())
                    .orElseThrow(() -> new ModeloNotFoundException("Product not found with ID: " + detailDTO.getProductId()));
            
            InventoryMovementDetail detail = new InventoryMovementDetail();
            detail.setProduct(product);
            detail.setQuantity(detailDTO.getQuantity());
            
            existingMovement.addDetail(detail);
        }

        return inventoryMovementRepo.save(existingMovement);
    }
    
    @Override
    public void delete(Integer id) throws Exception {
        if (!inventoryMovementRepo.existsById(id)) {
            throw new ModeloNotFoundException("Inventory movement not found with ID: " + id);
        }
        inventoryMovementRepo.deleteById(id);
    }
    // <-- ESTE ES EL MÉTODO CLAVE QUE FALTABA IMPLEMENTAR
    @Override
    @Transactional
    public void createMovementFromPurchase(Purchase purchase) throws Exception {
        
        // 1. Crear el objeto de movimiento de inventario
        InventoryMovement movement = new InventoryMovement();
        
        movement.setMovementType("INGRESO"); 
        movement.setMovementDate(LocalDateTime.now());
        
        movement.setDocumentType("PURCHASE"); 
        movement.setIdMov(purchase.getId());
        
        movement.setUser(purchase.getResponsibleUser());
        movement.setAdministrativeUnit(purchase.getUnidadAdministrativaCompra());
        
        // 2. Crear los detalles del movimiento y actualizar el stock
        for (PurchaseDetail purchaseDetail : purchase.getPurchaseDetails()) {
            InventoryMovementDetail movementDetail = new InventoryMovementDetail();
            movementDetail.setProduct(purchaseDetail.getProduct());
            movementDetail.setQuantity(purchaseDetail.getQuantity());
            
            movement.addDetail(movementDetail);
            
            // ✅ Llamada al servicio que aumenta el stock
            Integer productId = purchaseDetail.getProduct().getId();
            Integer quantity = purchaseDetail.getQuantity();
            Integer unidadAdministrativaId = purchase.getUnidadAdministrativaCompra().getIdUniAdm();
            
            inventoryUnitService.aumentarStock(productId, unidadAdministrativaId, quantity);
        }
        
        // 3. Guardar el movimiento completo
        inventoryMovementRepo.save(movement);
    }
    @Override
    @Transactional
    public void createMovementFromDelivery(Delivery delivery) throws Exception {

        InventoryMovement movement = new InventoryMovement();

        // ✅ CORRECCIÓN: Usar el valor del deliveryTypeDetail para obtener el tipo de movimiento
        String movementType = delivery.getDeliveryTypeDetail().getValue(); 
        movement.setMovementType(movementType); 
        movement.setMovementDate(delivery.getDeliveryDate()); 
        movement.setDocumentType("DELIVERY");
        movement.setIdMov(delivery.getId());

        // Asignar las relaciones
        movement.setUser(delivery.getUser());
        
        if (delivery.getDeliveryDetails() != null && !delivery.getDeliveryDetails().isEmpty()) {
            InventoryUnit inventoryUnit = delivery.getDeliveryDetails().get(0).getInventoryUnit();
            if (inventoryUnit != null && !inventoryUnit.getUnit().isEmpty()) {
                movement.setAdministrativeUnit(inventoryUnit.getUnit().get(0));
            }
        }

        // Crear los detalles del movimiento a partir de los detalles de la entrega
        for (DeliveryDetail deliveryDetail : delivery.getDeliveryDetails()) {
            InventoryMovementDetail movementDetail = new InventoryMovementDetail();
            movementDetail.setProduct(deliveryDetail.getProduct());
            movementDetail.setQuantity(deliveryDetail.getQuantity());

            movement.addDetail(movementDetail);
        }

        inventoryMovementRepo.save(movement);
    }
}