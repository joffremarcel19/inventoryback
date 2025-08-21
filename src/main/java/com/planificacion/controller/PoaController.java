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
import com.planificacion.model.ActividadesPoa;
import com.planificacion.model.DetallePoa;
import com.planificacion.model.ObjEstInstitucional;
import com.planificacion.model.Poa;
import com.planificacion.model.ReporteMensualPoa;
import com.planificacion.service.IActividadesDetallePoaService;
import com.planificacion.service.IDetallePoaService;
import com.planificacion.service.IObjEstInstitucionalService;
import com.planificacion.service.IPoaService;
import com.planificacion.service.IReportePoaService;

@RestController
@RequestMapping("/poa")
public class PoaController {

	@Autowired
	private IPoaService service;
	
	@Autowired
	private IObjEstInstitucionalService serviceObjInstitucional;
	
	@Autowired
	private IDetallePoaService detallePoaService;
	
	@Autowired
	private IActividadesDetallePoaService actividadDetallePoaService;

	@Autowired
	private IReportePoaService reportePoaService;


	@GetMapping
	public ResponseEntity<List<Poa>> listar() throws Exception {
		List<Poa> lista = service.listar();
		return new ResponseEntity<List<Poa>>(lista, HttpStatus.OK);
	}
	
    @GetMapping("objestinstitucional")
	public ResponseEntity<List<ObjEstInstitucional>> listarObjInstitucional() throws Exception {
		List<ObjEstInstitucional> lista = serviceObjInstitucional.listar();
		return new ResponseEntity<List<ObjEstInstitucional>>(lista, HttpStatus.OK);
	}
      

	@GetMapping("/{id}")
	public ResponseEntity<Poa> listarPorId(@PathVariable("id") Integer id) throws Exception {
		Poa obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<Poa>(obj, HttpStatus.OK);
	}

	
	@GetMapping("anio/{anio}/{idUni}")
	public ResponseEntity<List<Poa>> listarPorAnio(@PathVariable("anio") Integer anio, @PathVariable("idUni") Integer idUni) throws Exception {
		List<Poa> obj = service.listarPoaAnio(anio, idUni);
		
		
		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + anio);
		}
		return new ResponseEntity<List<Poa>>(obj, HttpStatus.OK);
	}
	
	@GetMapping("anio/{anio}")
	public ResponseEntity<List<Poa>> listarPoaPorAnio(@PathVariable("anio") Integer anio) throws Exception {
		List<Poa> obj = service.listarPoaPorAnio(anio);
		
		
		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + anio);
		}
		return new ResponseEntity<List<Poa>>(obj, HttpStatus.OK);
	}
	

	@PostMapping
	public ResponseEntity<Poa> registrar(@Valid @RequestBody Poa p) throws Exception {
		Poa obj = service.registrar(p);

		// localhost:8080/pacientes/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdPoa()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping
	public ResponseEntity<Poa> modificar(@Valid @RequestBody Poa p) throws Exception {
		Poa obj = service.modificar(p);
		return new ResponseEntity<Poa>(obj, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
		Poa obj = service.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		service.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping("/pageable")
	public ResponseEntity<Page<Poa>> listarPageable(Pageable pageable) throws Exception{
		Page<Poa> pacientes = service.listarPageable(pageable);
		return new ResponseEntity<Page<Poa>>(pacientes, HttpStatus.OK);
	}
	
	//Detalles de POA/
	@GetMapping("detallepoa/{idPoa}")
	public ResponseEntity<List<DetallePoa>> listarDetallePoa(@PathVariable("idPoa") Integer idPoa) throws Exception {
		List<DetallePoa> obj = detallePoaService.listarDetallePoa(idPoa);		
		
		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO ");
		}
		return new ResponseEntity<List<DetallePoa>>(obj, HttpStatus.OK);
	}
	
	@GetMapping("detallepoaid/{id}")
	public ResponseEntity<DetallePoa> listarDetallePorId(@PathVariable("id") Integer id) throws Exception {
		DetallePoa obj = detallePoaService.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<DetallePoa>(obj, HttpStatus.OK);
	}

	
	@PostMapping("registrarDetallePoa")
	public ResponseEntity<DetallePoa> registrarDetallePoa(@Valid @RequestBody DetallePoa p) throws Exception {
		DetallePoa obj = detallePoaService.registrar(p);

		// localhost:8080/pacientes/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdDetallePoa()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("modificarDetallePoa")
	public ResponseEntity<DetallePoa> modificarDetallePoa(@Valid @RequestBody DetallePoa p) throws Exception {
		DetallePoa obj = detallePoaService.modificar(p);
		return new ResponseEntity<DetallePoa>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("eliminardetalle/{id}")
	public ResponseEntity<Void> eliminarDetalle(@PathVariable("id") Integer id) throws Exception {
		DetallePoa obj = detallePoaService.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		 detallePoaService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	//Actividades de POA
	
	@GetMapping("actividaddetalle/{idDetallePoa}")
	public ResponseEntity<List<ActividadesPoa>> listarActividadesDetallePoa(@PathVariable("idDetallePoa") Integer idDetallePoa) throws Exception {
		List<ActividadesPoa> obj = actividadDetallePoaService.listarActividadesDetallePoa(idDetallePoa);		
		
		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO ");
		}
		return new ResponseEntity<List<ActividadesPoa>>(obj, HttpStatus.OK);
	}
	
	@GetMapping("actividadPoa/{id}")
	public ResponseEntity<ActividadesPoa> listarActividadPorId(@PathVariable("id") Integer id) throws Exception {
		ActividadesPoa obj = actividadDetallePoaService.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}
		return new ResponseEntity<ActividadesPoa>(obj, HttpStatus.OK);
	}
	
	@PostMapping("registraractividad")
	public ResponseEntity<ActividadesPoa> registrarActividadDetallePoa(@Valid @RequestBody ActividadesPoa p) throws Exception {
		ActividadesPoa obj = actividadDetallePoaService.registrar(p);

		// localhost:8080/pacientes/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdActividad()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("modificaractividad")
	public ResponseEntity<ActividadesPoa> modificarActividadDetallePoa(@Valid @RequestBody ActividadesPoa p) throws Exception {
		ActividadesPoa obj = actividadDetallePoaService.modificar(p);
		return new ResponseEntity<ActividadesPoa>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("eliminaractividad/{id}")
	public ResponseEntity<Void> eliminarActividad(@PathVariable("id") Integer id) throws Exception {
		ActividadesPoa obj = actividadDetallePoaService.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		actividadDetallePoaService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	//Reportes de Actividades.
	@GetMapping("reporteMensualPoa/{idActividadPoa}")
	public ResponseEntity<List<ReporteMensualPoa>> listarReportePoa(@PathVariable("idActividadPoa") Integer idActividadPoa) throws Exception {
		List<ReporteMensualPoa> obj = reportePoaService.listarReporteMensualPoaId(idActividadPoa);		
		
		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO ");
		}
		return new ResponseEntity<List<ReporteMensualPoa>>(obj, HttpStatus.OK);
	}
	
	@PostMapping("registrarreporte")
	public ResponseEntity<ReporteMensualPoa> registrarReportePoa(@Valid @RequestBody ReporteMensualPoa p) throws Exception {
		ReporteMensualPoa obj = reportePoaService.registrar(p);

		// localhost:8080/pacientes/2
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdReportePoa()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("modificarreporte")
	public ResponseEntity<ReporteMensualPoa> modificarReportePoa(@Valid @RequestBody ReporteMensualPoa p) throws Exception {
		ReporteMensualPoa obj = reportePoaService.modificar(p);
		return new ResponseEntity<ReporteMensualPoa>(obj, HttpStatus.OK);
	}
	
	@DeleteMapping("eliminarreporte/{id}")
	public ResponseEntity<Void> eliminarReportePoa(@PathVariable("id") Integer id) throws Exception {
		ReporteMensualPoa obj = reportePoaService.listarPorId(id);

		if (obj == null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
		}

		reportePoaService.eliminar(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
