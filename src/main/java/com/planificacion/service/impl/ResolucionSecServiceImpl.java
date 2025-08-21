package com.planificacion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planificacion.model.Menu;
import com.planificacion.model.ResolucionSec;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IMenuRepo;
import com.planificacion.repo.IResolucionSecRepo;
import com.planificacion.service.IMenuService;
import com.planificacion.service.IResolucionSecService;


@Service
public class ResolucionSecServiceImpl extends CRUDImpl<ResolucionSec, Integer> implements IResolucionSecService{

	@Autowired
	private IResolucionSecRepo repo;

	@Override
	protected IGenericRepo<ResolucionSec, Integer> getRepo() {
		return repo;
	}
}
