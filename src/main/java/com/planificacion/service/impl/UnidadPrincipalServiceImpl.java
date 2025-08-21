package com.planificacion.service.impl;

import com.planificacion.model.UnidadPrincipal;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IUnidadPrincipalRepo;
import com.planificacion.service.IUnidadPrincipalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnidadPrincipalServiceImpl extends CRUDImpl<UnidadPrincipal, Integer> implements IUnidadPrincipalService {

    @Autowired
    private IUnidadPrincipalRepo repo;

    @Override
    protected IGenericRepo<UnidadPrincipal, Integer> getRepo() {
        return repo;
    }

    // Aquí puedes agregar implementaciones específicas para UnidadPrincipal si las necesitas
}