package com.planificacion.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planificacion.model.Persona;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IPersonaRepo;
import com.planificacion.service.IPersonaService;

@Service
public class PersonaServiceImpl extends CRUDImpl<Persona, Integer> implements IPersonaService {

    @Autowired
    private IPersonaRepo repo;

    @Override
    protected IGenericRepo<Persona, Integer> getRepo() {
        return repo;
    }

    @Override
    public List<Persona> buscarPorNombreApellido(String nombres, String apellidos) {
        return repo.buscarPorNombreYApellido(nombres, apellidos);
    }

    @Override
    public List<Persona> buscarPorNombre(String nombres) {
        return repo.buscarPorNombre(nombres);
    }

    @Override
    public List<Persona> buscarPorApellido(String apellidos) {
        return repo.buscarPorApellido(apellidos);
    }

    @Override
    public Persona buscarPorCedula(String cedula) {
        return repo.findByCedula(cedula);
    }

    @Override
    public List<Persona> listarPorUnidadAdministrativa(Integer idUnidad) {
        return repo.findByUnidadAdministrativa_IdUnidad(idUnidad);
    }
    @Override
    public List<Persona> listarPorUnidadPrincipal(Integer idUnidadPrincipal) throws Exception {
        return repo.findByUnidadAdministrativa_UnidadPrincipal_Id(idUnidadPrincipal);
    }

    @Override
    public List<Persona> buscarPorRangoCedula(String cedula) {
        return repo.buscarPorRangoCedula(cedula);
    }
}