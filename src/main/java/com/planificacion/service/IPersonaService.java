package com.planificacion.service;

import java.util.List;

import com.planificacion.model.Persona;

public interface IPersonaService extends ICRUD<Persona, Integer> {

    List<Persona> buscarPorNombreApellido(String nombres, String apellidos);

    List<Persona> buscarPorNombre(String nombres);

    List<Persona> buscarPorApellido(String apellidos);

    Persona buscarPorCedula(String cedula);

    List<Persona> listarPorUnidadAdministrativa(Integer idUnidad);

    // Nuevo método para buscar cédulas por rango
    List<Persona> buscarPorRangoCedula(String cedula);
    
    // Nuevo método para listar personas por Unidad Principal
    List<Persona> listarPorUnidadPrincipal(Integer idUnidadPrincipal) throws Exception;
}