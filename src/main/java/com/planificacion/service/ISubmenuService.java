package com.planificacion.service;

import com.planificacion.model.Submenu;
import java.util.List;

public interface ISubmenuService extends ICRUD<Submenu, Integer> {
    
    List<Submenu> listarSubmenuPorUsuario(String nombre);
}