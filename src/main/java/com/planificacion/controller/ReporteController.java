package com.planificacion.controller;


import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.planificacion.exception.ModeloNotFoundException;
import com.planificacion.model.MetaInstitucional;
import com.planificacion.model.ObjEstInstitucional;
import com.planificacion.model.ObjEstTer1;
import com.planificacion.model.ObjEstTer2;
import com.planificacion.model.ReporteMensual;
import com.planificacion.service.IMetaInstitucionalService;
import com.planificacion.service.IObjEstInstitucionalService;
import com.planificacion.service.IObjEstTer1Service;
import com.planificacion.service.IObjEstTer2Service;
import com.planificacion.service.IReporteMensualService;

@RestController
@RequestMapping("/reportemensual")
public class ReporteController {

	@Autowired
	private IReporteMensualService service;
	
	@GetMapping
	public ResponseEntity<List<ReporteMensual>> listar() throws Exception {
		List<ReporteMensual> lista = service.listar();
		return new ResponseEntity<List<ReporteMensual>>(lista, HttpStatus.OK);
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<ReporteMensual> listarPorId(@PathVariable("id") Integer id) throws Exception {
		ReporteMensual obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<ReporteMensual>(obj, HttpStatus.OK);
	}

	
	@GetMapping("reporte/{idReporte}")
	public ResponseEntity<List<ReporteMensual>> listarPorAnio(@PathVariable("idReporte") Integer idReporte) throws Exception {
		List<ReporteMensual> obj = service.listarReporteMensualId(idReporte);		
		
		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO ");
		}
		return new ResponseEntity<List<ReporteMensual>>(obj, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ReporteMensual> registrar(@Valid @RequestBody ReporteMensual p) throws Exception {
		ReporteMensual obj = service.registrar(p);

		// localhost:8080/pacientes/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdReporte()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<ReporteMensual> modificar(@Valid @RequestBody ReporteMensual p) throws Exception {
		ReporteMensual obj = service.modificar(p);
		return new ResponseEntity<ReporteMensual>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		ReporteMensual obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
	/*@GetMapping("/pageable")
	public ResponseEntity<Page<ReporteMensual>> listarPageable(Pageable pageable) throws Exception{
		Page<ReporteMensual> pacientes = service.listarPageable(pageable);
		return new ResponseEntity<Page<ReporteMensual>>(pacientes, HttpStatus.OK);
	}*/
}
