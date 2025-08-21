package com.planificacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.planificacion.dto.PurchaseDTO;
import com.planificacion.dto.PurchaseRequestDTO;
import com.planificacion.model.Purchase;
import com.planificacion.service.IPurchaseService;

import javax.validation.Valid; // Para la validación de los DTOs

@RestController
@RequestMapping("/purchases") // Endpoint base para todas las operaciones de compra
public class PurchaseController {

    @Autowired
    private IPurchaseService purchaseService;

    // 1. Listar todas las compras con detalles
    // GET http://localhost:8081/purchases
    @GetMapping
    public ResponseEntity<List<PurchaseDTO>> getAllPurchasesWithDetails() throws Exception {
        List<PurchaseDTO> purchases = purchaseService.listAllPurchasesWithDetails();
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }

    // 2. Obtener una compra por ID con detalles
    // GET http://localhost:8081/purchases/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseDTO> getPurchaseByIdWithDetails(@PathVariable("id") Integer id) throws Exception {
        PurchaseDTO purchase = purchaseService.listPurchaseByIdWithDetails(id);
        return new ResponseEntity<>(purchase, HttpStatus.OK);
    }

    // 3. Registrar una nueva compra
    // POST http://localhost:8081/purchases
    @PostMapping
    public ResponseEntity<Purchase> registerPurchase(@Valid @RequestBody PurchaseRequestDTO request) throws Exception {
        // En una aplicación real, aquí podrías mapear la Purchase de respuesta a un DTO de respuesta
        Purchase newPurchase = purchaseService.register(request);
        return new ResponseEntity<>(newPurchase, HttpStatus.CREATED);
    }

    // 4. Actualizar una compra existente
    // PUT http://localhost:8081/purchases/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Purchase> updatePurchase(@PathVariable("id") Integer id, @Valid @RequestBody PurchaseRequestDTO request) throws Exception {
        // En una aplicación real, aquí podrías mapear la Purchase de respuesta a un DTO de respuesta
        Purchase updatedPurchase = purchaseService.update(id, request);
        return new ResponseEntity<>(updatedPurchase, HttpStatus.OK);
    }

    // 5. Eliminar una compra
    // DELETE http://localhost:8081/purchases/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePurchase(@PathVariable("id") Integer id) throws Exception {
        purchaseService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content
    }

    // 6. Obtener compras por ID de Usuario Responsable
    // GET http://localhost:8081/purchases/by-responsible-user?userId={responsibleUserId}
    @GetMapping("/by-responsible-user")
    public ResponseEntity<List<PurchaseDTO>> getPurchasesByResponsibleUser(@RequestParam("userId") Integer responsibleUserId) throws Exception {
        List<PurchaseDTO> purchases = purchaseService.getPurchasesByResponsibleUser(responsibleUserId);
        return new ResponseEntity<>(purchases, HttpStatus.OK);
    }
    @GetMapping("/by-unidad-administrativa/{unidadAdministrativaId}")
    public ResponseEntity<List<PurchaseDTO>> getPurchasesByUnidadAdministrativa(@PathVariable Integer unidadAdministrativaId) throws Exception {
        List<PurchaseDTO> purchases = purchaseService.getPurchasesByUnidadAdministrativa(unidadAdministrativaId);
        if (purchases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(purchases);
    }
    @GetMapping("/by-unidad-principal/{unidadPrincipalId}")
    public ResponseEntity<List<PurchaseDTO>> getPurchasesByUnidadPrincipal(@PathVariable Integer unidadPrincipalId) throws Exception {
        List<PurchaseDTO> purchases = purchaseService.getPurchasesByUnidadPrincipal(unidadPrincipalId);
        if (purchases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(purchases);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updatePurchaseStatus(@PathVariable Integer id, @RequestBody String status) throws Exception {
        String trimmedStatus = status.trim();
        purchaseService.updateStatus(id, trimmedStatus);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}