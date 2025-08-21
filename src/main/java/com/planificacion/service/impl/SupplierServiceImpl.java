package com.planificacion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.planificacion.model.Supplier;
import com.planificacion.repo.ISupplierRepo; // Importa tu repositorio específico
import com.planificacion.service.ISupplierService;

import java.util.List;

@Service
public class SupplierServiceImpl implements ISupplierService {

    @Autowired
    private ISupplierRepo supplierRepository; // Inyecta tu repositorio específico

    @Override
    public List<Supplier> listAll() throws Exception {
        return supplierRepository.findAll(); // Usa la instancia inyectada
    }

    @Override
    public Supplier listById(Integer id) throws Exception {
        return supplierRepository.findById(id) // Usa la instancia inyectada
                .orElseThrow(() -> new Exception("Supplier not found with id: " + id));
    }

    @Override
    public Supplier register(Supplier supplier) throws Exception {
        return supplierRepository.save(supplier); // Usa la instancia inyectada
    }

    @Override
    public Supplier update(Supplier supplier) throws Exception {
        // Aquí podrías agregar validaciones antes de guardar
        return supplierRepository.save(supplier); // Usa la instancia inyectada
    }

    @Override
    public void delete(Integer id) throws Exception {
        if (!supplierRepository.existsById(id)) {
            throw new Exception("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id); // Usa la instancia inyectada
    }
}