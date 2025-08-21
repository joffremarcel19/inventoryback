package com.planificacion.service;

import com.planificacion.model.Parameter;
import java.util.List;
import java.util.Optional;

public interface IParameterService {
    Parameter createParameter(Parameter parameter);
    Optional<Parameter> getParameterById(Integer id);
    List<Parameter> getAllParameters();
    Parameter updateParameter(Integer id, Parameter updatedParameter);
    void deleteParameter(Integer id);
}
