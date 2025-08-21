package com.planificacion.controller;

import com.planificacion.model.ParameterDetail;
import com.planificacion.service.IParameterDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/parameter-details")
public class ParameterDetailController {

    @Autowired
    private IParameterDetailService parameterDetailService;

    @PostMapping
    public ResponseEntity<ParameterDetail> createParameterDetail(@RequestBody ParameterDetail parameterDetail) {
         parameterDetail.setId(null);
        ParameterDetail createdParameterDetail = parameterDetailService.createParameterDetail(parameterDetail);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(createdParameterDetail.getId())
                                .toUri();
        return ResponseEntity.created(location).body(createdParameterDetail);
    }

    @GetMapping
    public ResponseEntity<List<ParameterDetail>> getAllParameterDetails() {
        List<ParameterDetail> parameterDetails = parameterDetailService.getAllParameterDetails();
        return ResponseEntity.ok(parameterDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParameterDetail> getParameterDetailById(@PathVariable Integer id) {
        Optional<ParameterDetail> parameterDetail = parameterDetailService.getParameterDetailById(id);
        return parameterDetail.map(ResponseEntity::ok)
                           .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParameterDetail> updateParameterDetail(@PathVariable Integer id, @RequestBody ParameterDetail parameterDetail) {
        ParameterDetail updated = parameterDetailService.updateParameterDetail(id, parameterDetail);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParameterDetail(@PathVariable Integer id) {
        parameterDetailService.deleteParameterDetail(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-parameter-code/{parameterCode}")
    public ResponseEntity<List<ParameterDetail>> getParameterDetailsByParameterCode(@PathVariable String parameterCode) {
        List<ParameterDetail> parameterDetails = parameterDetailService.getParameterDetailsByParameterCode(parameterCode);
        return ResponseEntity.ok(parameterDetails);
    }
 // ¡NUEVO ENDPOINT! Para buscar por ID del parámetro padre
    @GetMapping("/by-parameter-id/{parameterId}")
    public ResponseEntity<List<ParameterDetail>> getParameterDetailsByParameterId(@PathVariable Integer parameterId) {
        List<ParameterDetail> parameterDetails = parameterDetailService.getParameterDetailsByParameterId(parameterId);
        if (parameterDetails.isEmpty()) {
            return ResponseEntity.notFound().build(); // Devuelve 404 si no encuentra detalles para ese ID
        }
        return ResponseEntity.ok(parameterDetails);
    }
    // ✅ Nuevo endpoint con filtrado
    @GetMapping("/by-parameter-id/{parameterId}/value/{value}")
    public ResponseEntity<List<ParameterDetail>> listByParameterIdAndValue(
            @PathVariable("parameterId") Integer parameterId,
            @PathVariable("value") String value) {
        
        List<ParameterDetail> result = parameterDetailService.listByParameterIdAndValue(parameterId, value);
        return ResponseEntity.ok(result);
    }
}