package com.planificacion.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.planificacion.model.Menu;
import com.planificacion.model.Pei;
import com.planificacion.model.Usuario;

public interface IPeiService extends ICRUD<Pei, Integer>{
	
	Page<Pei> listarPageable(Pageable pageable);
	List<Pei> listarPeiAnio(Integer anio, Integer idUni);

}
