package com.planificacion.service.impl;

import com.planificacion.model.InventoryUnit;
import com.planificacion.model.Persona;
import com.planificacion.model.Product;
import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.repo.IInventoryUnitRepo;
import com.planificacion.repo.IPersonaRepo;
import com.planificacion.repo.IProductRepo;
import com.planificacion.repo.IUnidadAdministrativaRepo;
import com.planificacion.service.IInventoryUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

@Service
public class InventoryUnitServiceImpl implements IInventoryUnitService {

    @Autowired
    private IInventoryUnitRepo repo;

    @Autowired
    private IProductRepo productRepo; // Necesitas este repositorio

    @Autowired
    private IUnidadAdministrativaRepo unidadAdminRepo; // Necesitas este repositorio

    @Autowired
    private IPersonaRepo personaRepo;
    @Override
    public List<InventoryUnit> listar() {
        return repo.findAll();
    }

    @Override
    public InventoryUnit listarPorId(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public InventoryUnit registrar(InventoryUnit inventoryUnit) {
        // Validación básica
        Product productRequest = inventoryUnit.getProduct();
        if (productRequest == null || inventoryUnit.getUnit() == null || inventoryUnit.getUnit().isEmpty()) {
            throw new IllegalArgumentException("El producto y la unidad administrativa son requeridos.");
        }
        
        // ✅ Corregido: Buscar el objeto Product completo
        Product fullProduct = productRepo.findById(productRequest.getId())
                                        .orElseThrow(() -> new RuntimeException("Producto no encontrado."));
        inventoryUnit.setProduct(fullProduct);
        
        // ✅ Corregido: Buscar los objetos UnidadAdministrativa completos
        List<UnidadAdministrativa> fullUnits = inventoryUnit.getUnit().stream()
            .map(unit -> unidadAdminRepo.findById(unit.getIdUniAdm())
                                        .orElseThrow(() -> new RuntimeException("Unidad Administrativa no encontrada con ID: " + unit.getIdUniAdm())))
            .collect(Collectors.toList());
        inventoryUnit.setUnit(fullUnits);
        
        // ✅ Lógica para el campo 'custodian'
        if (inventoryUnit.getCustodian() != null && inventoryUnit.getCustodian().getIdPersona() != null) {
            Persona custodian = personaRepo.findById(inventoryUnit.getCustodian().getIdPersona())
                    .orElseThrow(() -> new RuntimeException("Persona (custodio) no encontrada con ID: " + inventoryUnit.getCustodian().getIdPersona()));
            inventoryUnit.setCustodian(custodian);
        }

        // ✅ Lógica para el campo 'status'
        // No se necesita ninguna validación especial

        // Obtener la primera unidad administrativa de la lista para la validación
        UnidadAdministrativa unidadAdministrativa = inventoryUnit.getUnit().get(0);
        
        // ➡️ Lógica condicional basada en el tipo de producto
        String productType = fullProduct.getType().name();

        if ("EquipoInformatico".equalsIgnoreCase(productType)) {
            // Lógica para Equipos Informáticos
            return repo.save(inventoryUnit);
        } else if ("Suministro".equalsIgnoreCase(productType)) {
            // Lógica para Suministros
            Optional<InventoryUnit> existingInventoryUnit = repo.findByProductAndUnidadAdministrativa(fullProduct, unidadAdministrativa);

            if (existingInventoryUnit.isPresent()) {
                throw new DuplicateKeyException("El producto con ID " + fullProduct.getId() + " ya existe en el inventario para la unidad administrativa con ID " + unidadAdministrativa.getIdUniAdm() + ". Use la función de modificar para agregar más stock.");
            }
            return repo.save(inventoryUnit);
        } else {
            throw new IllegalArgumentException("Tipo de producto no reconocido.");
        }
    }

    @Override
    public InventoryUnit modificar(InventoryUnit i) {
        return repo.save(i);
    }

    @Override
    public void eliminar(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public List<InventoryUnit> buscarPorNombreProducto(String name) {
        return repo.buscarPorNombreProducto(name);
    }

    @Override
    public List<InventoryUnit> listarPorUnidadPrincipal(Integer idUnidadP) {
        return repo.findByUnit_UnidadPrincipal_IdUniP(idUnidadP);
    }
 // En InventoryUnitServiceImpl.java -> método aumentarStock
    @Override
    public InventoryUnit aumentarStock(Integer productId, Integer unidadAdministrativaId, Integer cantidad) {
        // ✅ CORRECCIÓN: Crear las variables product y unidadAdmin
        Product product = new Product();
        product.setId(productId);
        
        UnidadAdministrativa unidadAdmin = new UnidadAdministrativa();
        unidadAdmin.setIdUniAdm(unidadAdministrativaId);
        
        // Ahora, el siguiente código ya no dará error
        Optional<InventoryUnit> existingUnit = repo.findByProductAndUnidadAdministrativa(product, unidadAdmin);

        if (existingUnit.isPresent()) {
            InventoryUnit inventoryUnit = existingUnit.get();
            inventoryUnit.setStock(inventoryUnit.getStock() + cantidad);
            return repo.save(inventoryUnit);
        } else {
            throw new IllegalArgumentException("La unidad de inventario para el producto " + productId + " y la unidad administrativa " + unidadAdministrativaId + " no existe.");
        }
    }
    
    
    // ✅ Implementación del nuevo método
    @Override
    public List<InventoryUnit> listarPorUnidadAdministrativa(Integer idUniAdm) {
        return repo.findByUnit_IdUniAdm(idUniAdm);
    }
    
    @Override
    @Transactional
    public void disminuirStock(Integer productId, Integer unidadAdministrativaId, Integer cantidad) throws Exception {
        // ✅ Usar el mismo método de repositorio para buscar
        Optional<InventoryUnit> optionalUnit = repo.findByProduct_IdAndUnit_IdUniAdm(productId, unidadAdministrativaId);

        if (optionalUnit.isPresent()) {
            InventoryUnit inventoryUnit = optionalUnit.get();
            if (inventoryUnit.getStock() < cantidad) {
                throw new Exception("Stock insuficiente para el producto.");
            }
            inventoryUnit.setStock(inventoryUnit.getStock() - cantidad);
            repo.save(inventoryUnit);
        } else {
            throw new Exception("No se encontró la unidad de inventario para disminuir el stock.");
        }
    }
}