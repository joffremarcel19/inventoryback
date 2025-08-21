package com.planificacion.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.planificacion.model.Calificacion;
import com.planificacion.model.Categoria;
import com.planificacion.model.Empresa;
import com.planificacion.model.Parametro;
import com.planificacion.repo.ICalificacionRepo;
import com.planificacion.repo.ICategoriaRepo;
import com.planificacion.repo.IDetallePeiRepo;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IParametroRepo;
import com.planificacion.service.ArchivoService;
import com.planificacion.service.ICalificacionService;
import com.planificacion.service.ICategoriaService;
import com.planificacion.service.IParametroService;

@Service
public class ParametroServiceImpl extends CRUDImpl<Parametro, Integer> implements IParametroService {

	@Autowired
	private IParametroRepo repo;

	@Override
	protected IGenericRepo<Parametro, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	
}
