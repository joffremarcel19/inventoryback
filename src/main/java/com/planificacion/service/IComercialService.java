package com.planificacion.service;

import java.util.List;

import com.planificacion.model.ActividadesPoa;
import com.planificacion.model.Calificacion;
import com.planificacion.model.Comercial;

public interface IComercialService extends ICRUD<Comercial, Integer> {
	List<Comercial> listarComercios(Integer idCategoria);
}
