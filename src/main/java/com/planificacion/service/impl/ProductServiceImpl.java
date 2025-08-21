package com.planificacion.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.planificacion.model.Product;
import com.planificacion.repo.IProductRepo;
import com.planificacion.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepo repo;

    @Override
    public List<Product> listar() throws Exception {
        return repo.findAll();
    }

    @Override
    public Product listarPorId(Integer id) throws Exception {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Product registrar(Product p) throws Exception {
        return repo.save(p);
    }

    @Override
    public Product modificar(Product p) throws Exception {
        return repo.save(p);
    }

    @Override
    public void eliminar(Integer id) throws Exception {
        repo.deleteById(id);
    }
}
