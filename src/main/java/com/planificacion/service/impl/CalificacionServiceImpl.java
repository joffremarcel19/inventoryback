package com.planificacion.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planificacion.model.Calificacion;
import com.planificacion.repo.ICalificacionRepo;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.service.ICalificacionService;

@Service
public class CalificacionServiceImpl extends CRUDImpl<Calificacion, Integer> implements ICalificacionService {

	@Autowired
	private ICalificacionRepo repo;

	@Override
	protected IGenericRepo<Calificacion, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public Calificacion consultaCalificacion(Integer idComercial, Integer idParametro, Integer idUsuario) {
		// TODO Auto-generated method stub
		return repo.consultaCalificacion(idComercial, idParametro, idUsuario);
	}
}
