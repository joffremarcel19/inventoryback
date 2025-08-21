package com.planificacion.repo;

import com.planificacion.model.UnidadPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUnidadPrincipalRepo extends IGenericRepo<UnidadPrincipal, Integer> {
    // Puedes agregar métodos específicos para UnidadPrincipal aquí
}