package com.planificacion.repo;

import com.planificacion.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IParameterRepo extends JpaRepository<Parameter, Integer> {
    // Métodos personalizados si los necesitas
}