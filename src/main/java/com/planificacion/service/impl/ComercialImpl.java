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
import com.planificacion.model.Comercial;
import com.planificacion.model.Empresa;
import com.planificacion.repo.ICalificacionRepo;
import com.planificacion.repo.IComercialRepo;
import com.planificacion.repo.IDetallePeiRepo;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.service.ArchivoService;
import com.planificacion.service.ICalificacionService;
import com.planificacion.service.IComercialService;

@Service
public class ComercialImpl extends CRUDImpl<Comercial, Integer> implements IComercialService {

	@Autowired
	private IComercialRepo repo;

	@Override
	protected IGenericRepo<Comercial, Integer> getRepo() {
		// TODO Auto-generated method stub
		return repo;
	}

	@Override
	public List<Comercial> listarComercios(Integer idCategoria) {
		// TODO Auto-generated method stub
		return repo.listarComercios(idCategoria);
	}
}
