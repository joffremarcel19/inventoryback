package com.planificacion.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planificacion.model.Calificacion;
import com.planificacion.model.Comercial;

public interface ICalificacionRepo extends IGenericRepo<Calificacion, Integer> {

	@Query(value = "select * from calificacion c where c.id_comercial = :idComercial and c.id_parametro=:idParametro and id_usuario=:idUsuario", nativeQuery = true)
	Calificacion consultaCalificacion(@Param("idComercial") int idComercial, @Param("idParametro") int idParametro,
			@Param("idUsuario") int idUsuario);
}
