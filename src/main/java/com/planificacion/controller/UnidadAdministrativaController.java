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
import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.service.IMetaInstitucionalService;
import com.planificacion.service.IObjEstInstitucionalService;
import com.planificacion.service.IObjEstTer1Service;
import com.planificacion.service.IObjEstTer2Service;
import com.planificacion.service.IUnidadAdministrativaService;

@RestController
@RequestMapping("/unidadAdministrativa")
public class UnidadAdministrativaController {

	@Autowired
	private IUnidadAdministrativaService service;
	
	@Autowired
	private IObjEstTer1Service serviceObjEstTer1;
	
	@Autowired
	private IObjEstInstitucionalService serviceObjInstitucional;
	
	@Autowired
	private IMetaInstitucionalService serviceMetaInstitucional;


	@GetMapping
	public ResponseEntity<List<UnidadAdministrativa>> listar() throws Exception {
		List<UnidadAdministrativa> lista = service.listar();
		return new ResponseEntity<List<UnidadAdministrativa>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("objestter1")
	public ResponseEntity<List<ObjEstTer1>> listarObjEstTer1() throws Exception {
		List<ObjEstTer1> lista = serviceObjEstTer1.listar();
		return new ResponseEntity<List<ObjEstTer1>>(lista, HttpStatus.OK);
	}
	
    @GetMapping("objestinstitucional")
	public ResponseEntity<List<ObjEstInstitucional>> listarObjInstitucional() throws Exception {
		List<ObjEstInstitucional> lista = serviceObjInstitucional.listar();
		return new ResponseEntity<List<ObjEstInstitucional>>(lista, HttpStatus.OK);
	}
    
    @GetMapping("metainstitucional")
   	public ResponseEntity<List<MetaInstitucional>> listarMetaInstitucional() throws Exception {
   		List<MetaInstitucional> lista = serviceMetaInstitucional.listar();
   		return new ResponseEntity<List<MetaInstitucional>>(lista, HttpStatus.OK);
   	}

	@GetMapping("/{id}")
	public ResponseEntity<UnidadAdministrativa> listarPorId(@PathVariable("id") Integer id) throws Exception {
		UnidadAdministrativa obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<UnidadAdministrativa>(obj, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<UnidadAdministrativa> registrar(@Valid @RequestBody UnidadAdministrativa p) throws Exception {
		UnidadAdministrativa obj = service.registrar(p);

		// localhost:8080/pacientes/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdUniAdm()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<UnidadAdministrativa> modificar(@Valid @RequestBody UnidadAdministrativa p) throws Exception {
		UnidadAdministrativa obj = service.modificar(p);
		return new ResponseEntity<UnidadAdministrativa>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		UnidadAdministrativa obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	@GetMapping("/unidadPrincipal/{idUnidadPrincipal}")
    public ResponseEntity<List<UnidadAdministrativa>> listarPorUnidadPrincipal(@PathVariable("idUnidadPrincipal") Integer idUnidadPrincipal) throws Exception {
        List<UnidadAdministrativa> lista = service.listarPorUnidadPrincipal(idUnidadPrincipal);
        if (lista.isEmpty()) {
            throw new ModeloNotFoundException("No se encontraron unidades administrativas para la Unidad Principal con ID: " + idUnidadPrincipal);
        }
        return new ResponseEntity<List<UnidadAdministrativa>>(lista, HttpStatus.OK);
    }
	
	
}
