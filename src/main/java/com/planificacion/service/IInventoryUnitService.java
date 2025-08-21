package com.planificacion.service;

import java.util.List;

import com.planificacion.model.InventoryUnit;

public interface IInventoryUnitService {
    List<InventoryUnit> listar();
    InventoryUnit listarPorId(Integer id);
    InventoryUnit registrar(InventoryUnit i);
    InventoryUnit modificar(InventoryUnit i);
    void eliminar(Integer id);
    List<InventoryUnit> buscarPorNombreProducto(String name);

    // Nuevo método para buscar por ID de Unidad Principal
    List<InventoryUnit> listarPorUnidadPrincipal(Integer idUnidadP);
 // ✅ Agrega la firma del nuevo método aquí
    InventoryUnit aumentarStock(Integer productId, Integer unidadAdministrativaId, Integer cantidad);
    void disminuirStock(Integer productId, Integer unidadAdministrativaId, Integer cantidad) throws Exception;
    List<InventoryUnit> listarPorUnidadAdministrativa(Integer idUniAdm);
}