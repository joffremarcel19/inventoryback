package com.planificacion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planificacion.model.Menu;
import com.planificacion.model.Pei;
import com.planificacion.model.ReporteMensual;
import com.planificacion.model.Rol;
import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IMenuRepo;
import com.planificacion.repo.IPeiRepo;
import com.planificacion.repo.IReporteMensualRepo;
import com.planificacion.repo.IRolRepo;
import com.planificacion.repo.IUnidadAdministrativaRepo;
import com.planificacion.service.IMenuService;
import com.planificacion.service.IPeiService;
import com.planificacion.service.IReporteMensualService;
import com.planificacion.service.IRolService;
import com.planificacion.service.IUnidadAdministrativaService;


@Service
public class ReporteMensualServiceImpl extends CRUDImpl<ReporteMensual, Integer> implements IReporteMensualService{

	@Autowired
	private IReporteMensualRepo repo;

	@Override
	protected IGenericRepo<ReporteMensual, Integer> getRepo() {
		return repo;
	}	
	
	@Override
	public List<ReporteMensual> listarReporteMensualId(Integer idPei) {
		// TODO Auto-generated method stub
		return repo.listarReporteMensualId(idPei);
	}

}
