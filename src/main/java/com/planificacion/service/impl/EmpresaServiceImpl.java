package com.planificacion.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planificacion.model.Empresa;
import com.planificacion.repo.IEmpresaRepo;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.service.IEmpresaService;

@Service
public class EmpresaServiceImpl extends CRUDImpl<Empresa, Integer> implements IEmpresaService{

	@Autowired
	private IEmpresaRepo repo;

	@Override
	protected IGenericRepo<Empresa, Integer> getRepo() {
		return repo;
	}	
	

}
