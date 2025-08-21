package com.planificacion.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planificacion.model.ActividadesPoa;
import com.planificacion.model.DetallePei;
import com.planificacion.model.DetallePoa;

public interface IActividadesDetallePoaRepo extends IGenericRepo<ActividadesPoa, Integer>  {
	@Query(value = "select * from actividades_poa r where r.id_detalle_poa = :idDetallePoa", nativeQuery = true)
	List<ActividadesPoa> listarActividadesDetallePoa(@Param("idDetallePoa") int idDetallePoa);
}
