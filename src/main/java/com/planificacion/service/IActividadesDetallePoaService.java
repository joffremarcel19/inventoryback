package com.planificacion.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.planificacion.model.ActividadesPoa;
import com.planificacion.model.DetallePei;
import com.planificacion.model.DetallePoa;
import com.planificacion.model.Menu;
import com.planificacion.model.Pei;
import com.planificacion.model.Usuario;

public interface IActividadesDetallePoaService extends ICRUD<ActividadesPoa, Integer>{
	
	Page<ActividadesPoa> listarPageable(Pageable pageable);
	List<ActividadesPoa> listarActividadesDetallePoa(Integer idDetallePoa);

}
