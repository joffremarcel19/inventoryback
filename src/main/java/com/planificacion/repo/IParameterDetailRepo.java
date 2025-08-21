package com.planificacion.repo;

import com.planificacion.model.ParameterDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IParameterDetailRepo extends JpaRepository<ParameterDetail, Integer> {
    // Métodos personalizados si los necesitas
	List<ParameterDetail> findByParameterId(Integer parameterId);
	 // ✅ Nuevo método para buscar por parameter_id y value
    List<ParameterDetail> findByParameter_IdAndValue(Integer parameterId, String value);
}