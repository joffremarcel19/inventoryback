package com.planificacion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.planificacion.model.Menu;
import com.planificacion.model.Pei;
import com.planificacion.model.Poa;
import com.planificacion.model.Rol;
import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IMenuRepo;
import com.planificacion.repo.IPeiRepo;
import com.planificacion.repo.IPoaRepo;
import com.planificacion.repo.IRolRepo;
import com.planificacion.repo.IUnidadAdministrativaRepo;
import com.planificacion.service.IMenuService;
import com.planificacion.service.IPeiService;
import com.planificacion.service.IPoaService;
import com.planificacion.service.IRolService;
import com.planificacion.service.IUnidadAdministrativaService;


@Service
public class PoaServiceImpl extends CRUDImpl<Poa, Integer> implements IPoaService{

	@Autowired
	private IPoaRepo repo;

	@Override
	protected IGenericRepo<Poa, Integer> getRepo() {
		return repo;
	}	
	
	@Override
	public Page<Poa> listarPageable(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public List<Poa> listarPoaAnio(Integer anio, Integer idUni) {
		// TODO Auto-generated method stub
		return repo.listarPoaAnio(anio, idUni);
	}

	@Override
	public List<Poa> listarPoaPorAnio(Integer anio) {
		// TODO Auto-generated method stub
		return repo.listarPoaPorAnio(anio);
	}



}
