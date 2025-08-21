package com.planificacion.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planificacion.model.DetallePei;

public interface IDetallePeiRepo extends IGenericRepo<DetallePei, Integer>  {
	@Query(value = "select * from detallepei r where r.id_pei = :idPei", nativeQuery = true)
	List<DetallePei> listarDetallePei(@Param("idPei") int idPei);
}
