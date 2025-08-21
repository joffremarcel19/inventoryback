package com.planificacion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planificacion.model.Menu;
import com.planificacion.model.ObjEstTer1;
import com.planificacion.model.ObjEstTer2;
import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IMenuRepo;
import com.planificacion.repo.IObjEstTer1Repo;
import com.planificacion.repo.IObjEstTer2Repo;
import com.planificacion.repo.IUnidadAdministrativaRepo;
import com.planificacion.service.IMenuService;
import com.planificacion.service.IObjEstTer1Service;
import com.planificacion.service.IObjEstTer2Service;
import com.planificacion.service.IUnidadAdministrativaService;


@Service
public class ObjEstTer2ServiceImpl extends CRUDImpl<ObjEstTer2, Integer> implements IObjEstTer2Service{

	@Autowired
	private IObjEstTer2Repo repo;

	@Override
	protected IGenericRepo<ObjEstTer2, Integer> getRepo() {
		return repo;
	}	
	

}
