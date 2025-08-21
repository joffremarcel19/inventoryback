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
import com.planificacion.model.Calificacion;
import com.planificacion.model.Categoria;
import com.planificacion.model.Comercial;
import com.planificacion.model.MetaInstitucional;
import com.planificacion.model.ObjEstInstitucional;
import com.planificacion.model.ObjEstTer1;
import com.planificacion.model.ObjEstTer2;
import com.planificacion.model.Parametro;
import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.service.ICalificacionService;
import com.planificacion.service.ICategoriaService;
import com.planificacion.service.IComercialService;
import com.planificacion.service.IMetaInstitucionalService;
import com.planificacion.service.IObjEstInstitucionalService;
import com.planificacion.service.IObjEstTer1Service;
import com.planificacion.service.IObjEstTer2Service;
import com.planificacion.service.IParametroService;
import com.planificacion.service.IUnidadAdministrativaService;

@RestController
@RequestMapping("/calificacion")
public class CalificacionController {

	@Autowired
	private ICategoriaService categoriaService;
	
	@Autowired
	private ICalificacionService calificacionService;
	
	@Autowired
	private IComercialService comercialService;
	
	@Autowired
	private IParametroService parametroService;


	@GetMapping
	public ResponseEntity<List<Calificacion>> listarCalificacion() throws Exception {
		List<Calificacion> lista = calificacionService.listar();
		return new ResponseEntity<List<Calificacion>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("parametro")
	public ResponseEntity<List<Parametro>> listarParametro() throws Exception {
		List<Parametro> lista = parametroService.listar();
		return new ResponseEntity<List<Parametro>>(lista, HttpStatus.OK);
	}
	
    @GetMapping("comercial")
	public ResponseEntity<List<Comercial>> listarComercio() throws Exception {
		List<Comercial> lista = comercialService.listar();
		return new ResponseEntity<List<Comercial>>(lista, HttpStatus.OK);
	}
    
    @GetMapping("categoria")
   	public ResponseEntity<List<Categoria>> listarCategoria() throws Exception {
   		List<Categoria> lista = categoriaService.listar();
   		return new ResponseEntity<List<Categoria>>(lista, HttpStatus.OK);
   	}
    
    @GetMapping("/comercial/{id}")
  	public ResponseEntity<List<Comercial>> listarComercioCategoria(@PathVariable("id") Integer idCategoria) throws Exception {
  		List<Comercial> lista = comercialService.listarComercios(idCategoria);
  		return new ResponseEntity<List<Comercial>>(lista, HttpStatus.OK);
  	}

	@GetMapping("/{id}")
	public ResponseEntity<Calificacion> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Calificacion obj = calificacionService.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Calificacion>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/local/{id}")
	public ResponseEntity<Comercial> listaComercialPorId(@PathVariable("id") Integer id) throws Exception {
		Comercial obj = comercialService.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Comercial>(obj, HttpStatus.OK);
	}
	
	@GetMapping("/consultacalificacion/{idComercial}/{idParametro}/{idUsuario}")
	public ResponseEntity<Calificacion> consultaCalificacion(@PathVariable("idComercial") Integer idComercial, @PathVariable("idParametro") Integer idParametro, @PathVariable("idUsuario") Integer idUsuario) throws Exception {
		Calificacion obj = calificacionService.consultaCalificacion(idComercial, idParametro, idUsuario);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + idComercial + idParametro+ idUsuario);
		}
		return new ResponseEntity<Calificacion>(obj, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Calificacion> registrar(@Valid @RequestBody Calificacion p) throws Exception {
		Calificacion obj = calificacionService.registrar(p);

		// localhost:8080/pacientes/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdCalificacion()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<Calificacion> modificar(@Valid @RequestBody Calificacion p) throws Exception {
		Calificacion obj = calificacionService.modificar(p);
		return new ResponseEntity<Calificacion>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Calificacion obj = calificacionService.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		calificacionService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
}
