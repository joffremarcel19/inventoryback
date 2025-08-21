package com.planificacion.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planificacion.model.ReporteMensual;
import com.planificacion.model.ReporteMensualPoa;

public interface IReportePoaRepo extends IGenericRepo<ReporteMensualPoa, Integer>  {
	@Query(value = "select * from reportemensual_poa r where r.id_actividad = :idActividad", nativeQuery = true)
	List<ReporteMensualPoa> listarReporteMensualPoaId(@Param("idActividad") int idDetallePei);
}
