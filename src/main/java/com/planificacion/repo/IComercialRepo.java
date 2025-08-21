package com.planificacion.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planificacion.model.ActividadesPoa;
import com.planificacion.model.Calificacion;
import com.planificacion.model.Comercial;

public interface IComercialRepo extends IGenericRepo<Comercial, Integer>  {
	@Query(value = "select * from comercial c where c.id_categoria = :idCategoria", nativeQuery = true)
	List<Comercial> listarComercios(@Param("idCategoria") int idCategoria);
}
