package com.planificacion.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planificacion.model.DetallePei;
import com.planificacion.model.DetallePoa;

public interface IDetallePoaRepo extends IGenericRepo<DetallePoa, Integer>  {
	@Query(value = "select * from detallepoa r where r.id_poa = :idPoa", nativeQuery = true)
	List<DetallePoa> listarDetallePoa(@Param("idPoa") int idPoa);
}
