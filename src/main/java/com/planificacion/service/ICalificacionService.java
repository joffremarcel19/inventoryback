package com.planificacion.service;

import java.util.List;

import com.planificacion.model.Calificacion;
import com.planificacion.model.Comercial;

public interface ICalificacionService extends ICRUD<Calificacion, Integer> {
	Calificacion consultaCalificacion(Integer idComercial, Integer idParametro, Integer idUsuario);
}
