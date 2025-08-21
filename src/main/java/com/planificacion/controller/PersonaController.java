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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.planificacion.exception.ModeloNotFoundException;
import com.planificacion.model.Persona;
import com.planificacion.service.IPersonaService;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    @Autowired
    private IPersonaService personaService;

    @GetMapping
    public ResponseEntity<List<Persona>> listar() throws Exception {
        List<Persona> lista = personaService.listar();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> listarPorId(@PathVariable("id") Integer id) throws Exception {
        Persona obj = personaService.listarPorId(id);
        if (obj == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> registrar(@Valid @RequestBody Persona persona) throws Exception {
        // Aquí podrías agregar validación para la cédula al registrar
        Persona obj = personaService.registrar(persona);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getIdPersona()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<Persona> modificar(@Valid @RequestBody Persona persona) throws Exception {
        // Aquí también podrías agregar validación para la cédula al modificar
        Persona obj = personaService.modificar(persona);
        return new ResponseEntity<>(obj, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable("id") Integer id) throws Exception {
        Persona obj = personaService.listarPorId(id);
        if (obj == null) {
            throw new ModeloNotFoundException("ID NO ENCONTRADO " + id);
        }
        personaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Persona>> buscarPersonas(
            @RequestParam(value = "nombres", required = false) String nombres,
            @RequestParam(value = "apellidos", required = false) String apellidos,
            @RequestParam(value = "cedula", required = false) String cedula) {

        List<Persona> personasEncontradas = null;

        if (cedula != null && !cedula.isEmpty()) {
            // Validación básica de la longitud de la cédula para la búsqueda
            if (cedula.length() == 10) {
                Persona persona = personaService.buscarPorCedula(cedula);
                if (persona != null) {
                    personasEncontradas = List.of(persona);
                }
            } else if (cedula.length() > 0) {
                // Si la cédula tiene algún valor pero no 10 dígitos, buscar por rango
                personasEncontradas = personaService.buscarPorRangoCedula(cedula);
            } else {
                personasEncontradas = null; // No buscar por cédula si está vacía
            }
        } else if (nombres != null && !nombres.isEmpty() && apellidos != null && !apellidos.isEmpty()) {
            personasEncontradas = personaService.buscarPorNombreApellido(nombres, apellidos);
        } else if (nombres != null && !nombres.isEmpty()) {
            personasEncontradas = personaService.buscarPorNombre(nombres);
        } else if (apellidos != null && !apellidos.isEmpty()) {
            personasEncontradas = personaService.buscarPorApellido(apellidos);
        } else {
            return ResponseEntity.badRequest().body(null); // Si no se proporciona ningún criterio de búsqueda
        }

        if (personasEncontradas != null && !personasEncontradas.isEmpty()) {
            return ResponseEntity.ok(personasEncontradas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/unidad/{idUnidad}")
    public ResponseEntity<List<Persona>> listarPorUnidadAdministrativa(@PathVariable("idUnidad") Integer idUnidad) throws Exception {
        List<Persona> lista = personaService.listarPorUnidadAdministrativa(idUnidad);
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
    @GetMapping("/unidadprincipal/{idUnidadPrincipal}")
    public ResponseEntity<List<Persona>> listarPorUnidadPrincipal(@PathVariable("idUnidadPrincipal") Integer idUnidadPrincipal) throws Exception {
        List<Persona> lista = personaService.listarPorUnidadPrincipal(idUnidadPrincipal);
        if (lista.isEmpty()) {
            throw new ModeloNotFoundException("No se encontraron personas para la Unidad Principal con ID: " + idUnidadPrincipal);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }
}
