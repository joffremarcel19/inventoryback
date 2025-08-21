package com.planificacion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.planificacion.model.DetallePei;
import com.planificacion.repo.IDetallePeiRepo;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.service.IDetallePeiService;

@Service
public class DetallePeiServiceImpl extends CRUDImpl<DetallePei, Integer> implements IDetallePeiService{

	@Autowired
	private IDetallePeiRepo repo;

	@Override
	protected IGenericRepo<DetallePei, Integer> getRepo() {
		return repo;
	}	
	
	@Override
	public Page<DetallePei> listarPageable(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public List<DetallePei> listarDetallePei(Integer idPei) {
		// TODO Auto-generated method stub
		return repo.listarDetallePei(idPei);
	}

	

}
