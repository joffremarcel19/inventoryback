package com.planificacion.service;

import java.util.List;
import com.planificacion.model.Supplier;

public interface ISupplierService {
    List<Supplier> listAll() throws Exception;
    Supplier listById(Integer id) throws Exception;
    Supplier register(Supplier supplier) throws Exception;
    Supplier update(Supplier supplier) throws Exception;
    void delete(Integer id) throws Exception;
}