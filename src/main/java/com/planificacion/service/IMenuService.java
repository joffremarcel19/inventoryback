package com.planificacion.service;

import java.util.List;

import com.planificacion.model.Menu;
import com.planificacion.model.Usuario;

public interface IMenuService extends ICRUD<Menu, Integer>{
	
	List<Menu> listarMenuPorUsuario(String nombre);

}
