package com.planificacion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.planificacion.model.DetallePei;
import com.planificacion.model.DetallePoa;
import com.planificacion.repo.IDetallePeiRepo;
import com.planificacion.repo.IDetallePoaRepo;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.service.IDetallePeiService;
import com.planificacion.service.IDetallePoaService;

@Service
public class DetallePoaServiceImpl extends CRUDImpl<DetallePoa, Integer> implements IDetallePoaService{

	@Autowired
	private IDetallePoaRepo repo;

	@Override
	protected IGenericRepo<DetallePoa, Integer> getRepo() {
		return repo;
	}	
	
	@Override
	public Page<DetallePoa> listarPageable(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public List<DetallePoa> listarDetallePoa(Integer idPei) {
		// TODO Auto-generated method stub
		return repo.listarDetallePoa(idPei);
	}

	

}
