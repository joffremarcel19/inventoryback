package com.planificacion.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planificacion.model.Poa;

public interface IPoaRepo extends IGenericRepo<Poa, Integer> {
	@Query(value = "select * from poa p where p.anio_reportado = :anio and p.id_uni_adm=:idUni", nativeQuery = true)
	List<Poa> listarPoaAnio(@Param("anio") int anio, @Param("idUni") int idUni);
	
	@Query(value = "SELECT * FROM poa p inner join unidadadministrativa u on p.id_uni_adm=u.id_uni_adm where p.anio_reportado = :anio", nativeQuery = true)
	List<Poa> listarPoaPorAnio(@Param("anio") int anio);
}
