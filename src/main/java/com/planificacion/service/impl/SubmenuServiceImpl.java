package com.planificacion.service.impl;

import com.planificacion.model.Submenu;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.ISubmenuRepo;
import com.planificacion.service.ISubmenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmenuServiceImpl extends CRUDImpl<Submenu, Integer> implements ISubmenuService {

    @Autowired
    private ISubmenuRepo submenuRepo;

    @Override
    protected IGenericRepo<Submenu, Integer> getRepo() {
        return submenuRepo;
    }

    @Override
    public List<Submenu> listarSubmenuPorUsuario(String nombre) {
        return submenuRepo.listSubmenuByUsername(nombre);
    }
}