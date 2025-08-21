package com.planificacion.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

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
import org.springframework.http.MediaType;

import com.planificacion.exception.ModeloNotFoundException;
import com.planificacion.model.Empresa;
import com.planificacion.model.Impresion;
import com.planificacion.model.Persona;
import com.planificacion.service.IEmpresaService;
import com.planificacion.service.IImpresionService;
import com.planificacion.service.IPersonaService;

@RestController
@RequestMapping("/impresion")
public class ImpresionController {

	/*@Autowired
	private IImpresionService service;
	
	@Autowired
	private IPersonaService servicePersona;
	
	@Autowired
	private IEmpresaService serviceEmpresa;
	
	@GetMapping
	public ResponseEntity<List<Impresion>> listar() throws Exception {
		List<Impresion> lista = service.listar();
		return new ResponseEntity<List<Impresion>>(lista, HttpStatus.OK);
	}
		
	@GetMapping("/empresa")
	public ResponseEntity<List<Empresa>> listarEmpresa() throws Exception {
		List<Empresa> lista = serviceEmpresa.listar();
		return new ResponseEntity<List<Empresa>>(lista, HttpStatus.OK);
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<Impresion> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Impresion obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Impresion>(obj, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Impresion> registrar(@Valid @RequestBody Impresion p) throws Exception {
		Impresion obj = service.registrar(p);

		// localhost:8080/pacientes/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdImpresion()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<Impresion> modificar(@Valid @RequestBody Impresion p) throws Exception {
		Impresion obj = service.modificar(p);
		return new ResponseEntity<Impresion>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Impresion obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
    //PERSONAS
	
	@GetMapping("/personas/{idEmpresa}")
	public ResponseEntity<List<Persona>> listarPersonasEmpresas(@PathVariable("idEmpresa") Integer idEmpresa) throws Exception {
		List<Persona> lista = servicePersona.listarPersonasEmpresas(idEmpresa);
		return new ResponseEntity<List<Persona>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/persona/{id}")
	public ResponseEntity<Persona> listarPersonaPorId(@PathVariable("id") Integer id) throws Exception {
		Persona obj = servicePersona.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Persona>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/persona")
	public ResponseEntity<List<Persona>> listarPersona() throws Exception {
		List<Persona> lista = servicePersona.listar();
		return new ResponseEntity<List<Persona>>(lista, HttpStatus.OK);
	}
	
	@PostMapping("/persona")
	public ResponseEntity<Impresion> registrarPersona(@Valid @RequestBody Persona p) throws Exception {
		Persona obj = servicePersona.registrar(p);

		// localhost:8080/pacientes/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdPersona()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/persona")
	public ResponseEntity<Persona> modificarPersona(@Valid @RequestBody Persona p) throws Exception {
		Persona obj = servicePersona.modificar(p);
		return new ResponseEntity<Persona>(obj, HttpStatus.OK);
	}
	
	// Reporte	
	@GetMapping(value = "/generarReporte/{idImpresion}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<byte[]> generarReporte(@PathVariable("idImpresion") Integer idImpresion) {		
		byte[] data = null;
		data = service.generarReporte(idImpresion);
		return new ResponseEntity<byte[]>(data, HttpStatus.OK);
	}*/
	
	
}
