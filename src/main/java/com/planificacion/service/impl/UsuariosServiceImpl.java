package com.planificacion.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.planificacion.model.Menu;
import com.planificacion.model.Pei;
import com.planificacion.model.Rol;
import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.model.Usuario;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IMenuRepo;
import com.planificacion.repo.IPeiRepo;
import com.planificacion.repo.IRolRepo;
import com.planificacion.repo.IUnidadAdministrativaRepo;
import com.planificacion.repo.IUsuarioRepo;
import com.planificacion.service.IMenuService;
import com.planificacion.service.IPeiService;
import com.planificacion.service.IRolService;
import com.planificacion.service.IUnidadAdministrativaService;
import com.planificacion.service.IUsuarioService;


@Service
public class UsuariosServiceImpl extends CRUDImpl<Usuario, Integer> implements IUsuarioService{

	@Autowired
	private IUsuarioRepo repo;

	@Override
	protected IGenericRepo<Usuario, Integer> getRepo() {
		return repo;
	}		

}
