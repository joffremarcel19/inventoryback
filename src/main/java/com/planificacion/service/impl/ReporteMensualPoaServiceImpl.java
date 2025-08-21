package com.planificacion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planificacion.model.Menu;
import com.planificacion.model.Pei;
import com.planificacion.model.ReporteMensual;
import com.planificacion.model.ReporteMensualPoa;
import com.planificacion.model.Rol;
import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IMenuRepo;
import com.planificacion.repo.IPeiRepo;
import com.planificacion.repo.IReporteMensualRepo;
import com.planificacion.repo.IReportePoaRepo;
import com.planificacion.repo.IRolRepo;
import com.planificacion.repo.IUnidadAdministrativaRepo;
import com.planificacion.service.IMenuService;
import com.planificacion.service.IPeiService;
import com.planificacion.service.IReporteMensualService;
import com.planificacion.service.IReportePoaService;
import com.planificacion.service.IRolService;
import com.planificacion.service.IUnidadAdministrativaService;


@Service
public class ReporteMensualPoaServiceImpl extends CRUDImpl<ReporteMensualPoa, Integer> implements IReportePoaService{

	@Autowired
	private IReportePoaRepo repo;

	@Override
	protected IGenericRepo<ReporteMensualPoa, Integer> getRepo() {
		return repo;
	}	
	
	@Override
	public List<ReporteMensualPoa> listarReporteMensualPoaId(Integer idActividad) {
		// TODO Auto-generated method stub
		return repo.listarReporteMensualPoaId(idActividad);
	}

}
