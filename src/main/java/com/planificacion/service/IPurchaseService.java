package com.planificacion.service;

import java.util.List;

import com.planificacion.model.Purchase; // Mantenemos si los métodos register/update devuelven Purchase
import com.planificacion.dto.PurchaseDTO;
import com.planificacion.dto.PurchaseRequestDTO;

public interface IPurchaseService {
	Purchase findById(Integer id) throws Exception;

    // Este método es para obtener TODAS las compras con su unidad administrativa, unidad principal, etc.
    List<PurchaseDTO> listAllPurchasesWithDetails() throws Exception;

    // Este método es para obtener UNA compra por ID con su unidad administrativa, unidad principal, etc.
    PurchaseDTO listPurchaseByIdWithDetails(Integer id) throws Exception; // <-- ¡VERIFICA ESTA LÍNEA!

    // --- MÉTODOS PARA CREAR/ACTUALIZAR ---
    Purchase register(PurchaseRequestDTO request) throws Exception;
    Purchase update(Integer id, PurchaseRequestDTO request) throws Exception;

    // --- MÉTODO PARA FILTRAR POR USUARIO (retorna DTO) ---
    List<PurchaseDTO> getPurchasesByResponsibleUser(Integer responsibleUserId) throws Exception;
    List<PurchaseDTO> getPurchasesByUnidadAdministrativa(Integer unidadAdministrativaId) throws Exception;
    List<PurchaseDTO> getPurchasesByUnidadPrincipal(Integer unidadPrincipalId) throws Exception;

    // --- OTROS MÉTODOS ---
    void delete(Integer id) throws Exception;
 // En IPurchaseService.java

    void updateStatus(Integer id, String status) throws Exception;
}