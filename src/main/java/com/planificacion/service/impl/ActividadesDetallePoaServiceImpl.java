package com.planificacion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.planificacion.model.ActividadesPoa;
import com.planificacion.model.DetallePei;
import com.planificacion.model.DetallePoa;
import com.planificacion.repo.IActividadesDetallePoaRepo;
import com.planificacion.repo.IDetallePeiRepo;
import com.planificacion.repo.IDetallePoaRepo;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.service.IActividadesDetallePoaService;
import com.planificacion.service.IDetallePeiService;
import com.planificacion.service.IDetallePoaService;

@Service
public class ActividadesDetallePoaServiceImpl extends CRUDImpl<ActividadesPoa, Integer> implements IActividadesDetallePoaService{

	@Autowired
	private IActividadesDetallePoaRepo repo;

	@Override
	protected IGenericRepo<ActividadesPoa, Integer> getRepo() {
		return repo;
	}	
	
	@Override
	public Page<ActividadesPoa> listarPageable(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public List<ActividadesPoa> listarActividadesDetallePoa(Integer idDetallePoa) {
		// TODO Auto-generated method stub
		return repo.listarActividadesDetallePoa(idDetallePoa);
	}

	

}
