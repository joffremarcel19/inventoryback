package com.planificacion.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planificacion.model.ReporteMensual;

public interface IReporteMensualRepo extends IGenericRepo<ReporteMensual, Integer>  {
	@Query(value = "select * from reportemensual r where r.id_detalle_pei = :idDetallePei", nativeQuery = true)
	List<ReporteMensual> listarReporteMensualId(@Param("idDetallePei") int idDetallePei);
}
