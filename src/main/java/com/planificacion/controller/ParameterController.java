package com.planificacion.controller;

import com.planificacion.model.Parameter;
import com.planificacion.service.IParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parameters")
public class ParameterController {

    @Autowired
    private IParameterService parameterService;

    @PostMapping
    public ResponseEntity<Parameter> createParameter(@RequestBody Parameter parameter) {
        parameter.setId(null);
        Parameter createdParameter = parameterService.createParameter(parameter);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(createdParameter.getId())
                                .toUri();
        return ResponseEntity.created(location).body(createdParameter);
    }

    @GetMapping
    public ResponseEntity<List<Parameter>> getAllParameters() {
        List<Parameter> parameters = parameterService.getAllParameters();
        return ResponseEntity.ok(parameters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parameter> getParameterById(@PathVariable Integer id) {
        Optional<Parameter> parameter = parameterService.getParameterById(id);
        return parameter.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Parameter> updateParameter(@PathVariable Integer id, @RequestBody Parameter parameter) {
        Parameter updated = parameterService.updateParameter(id, parameter);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParameter(@PathVariable Integer id) {
        parameterService.deleteParameter(id);
        return ResponseEntity.noContent().build();
    }
}