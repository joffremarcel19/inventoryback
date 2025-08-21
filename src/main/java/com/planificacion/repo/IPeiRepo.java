package com.planificacion.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planificacion.model.Pei;

public interface IPeiRepo extends IGenericRepo<Pei, Integer> {
	@Query(value = "select * from pei p where p.anio_reportado = :anio and p.id_uni_adm=:idUni", nativeQuery = true)
	List<Pei> listarPeiAnio(@Param("anio") int anio, @Param("idUni") int idUni);
}
