package com.planificacion.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planificacion.model.Menu;
import com.planificacion.model.ResolucionSec;

public interface IResolucionSecRepo extends IGenericRepo<ResolucionSec, Integer>  {
	
}
