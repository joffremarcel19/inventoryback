package com.planificacion.service;

import java.util.List;

import com.planificacion.model.Pei;
import com.planificacion.model.ReporteMensual;
import com.planificacion.model.ReporteMensualPoa;
import com.planificacion.model.Usuario;

public interface IReportePoaService extends ICRUD<ReporteMensualPoa, Integer>{
	
	List<ReporteMensualPoa> listarReporteMensualPoaId(Integer idActividad);

}
