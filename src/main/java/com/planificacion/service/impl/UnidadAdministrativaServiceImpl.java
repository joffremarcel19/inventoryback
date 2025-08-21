package com.planificacion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IUnidadAdministrativaRepo;
import com.planificacion.service.IUnidadAdministrativaService;

@Service
public class UnidadAdministrativaServiceImpl extends CRUDImpl<UnidadAdministrativa, Integer> implements IUnidadAdministrativaService {

    @Autowired
    private IUnidadAdministrativaRepo repo;

    @Override
    protected IGenericRepo<UnidadAdministrativa, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<UnidadAdministrativa> listarPorUnidadPrincipal(Integer idUnidadPrincipal) throws Exception {
        return repo.findByUnidadPrincipal_Id(idUnidadPrincipal);
    }
}