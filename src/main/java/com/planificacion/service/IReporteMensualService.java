package com.planificacion.service;

import java.util.List;

import com.planificacion.model.Pei;
import com.planificacion.model.ReporteMensual;
import com.planificacion.model.Usuario;

public interface IReporteMensualService extends ICRUD<ReporteMensual, Integer>{
	
	List<ReporteMensual> listarReporteMensualId(Integer idDetallePei);

}
