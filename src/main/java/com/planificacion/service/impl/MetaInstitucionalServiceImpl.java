package com.planificacion.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planificacion.model.MetaInstitucional;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IMetaInstitucionalRepo;
import com.planificacion.service.IMetaInstitucionalService;


@Service
public class MetaInstitucionalServiceImpl extends CRUDImpl<MetaInstitucional, Integer> implements IMetaInstitucionalService{

	@Autowired
	private IMetaInstitucionalRepo repo;

	@Override
	protected IGenericRepo<MetaInstitucional, Integer> getRepo() {
		return repo;
	}	
	

}
