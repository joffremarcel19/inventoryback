package com.planificacion.service;

import java.util.List;
import com.planificacion.model.Product;

public interface IProductService {
    List<Product> listar() throws Exception;
    Product listarPorId(Integer id) throws Exception;
    Product registrar(Product p) throws Exception;
    Product modificar(Product p) throws Exception;
    void eliminar(Integer id) throws Exception;
}