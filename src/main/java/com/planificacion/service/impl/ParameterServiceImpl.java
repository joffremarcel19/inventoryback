package com.planificacion.service.impl;

import com.planificacion.model.Parameter;
import com.planificacion.repo.IParameterRepo;
import com.planificacion.service.IParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ParameterServiceImpl implements IParameterService {

    @Autowired
    private IParameterRepo parameterRepo;

    @Override
    public Parameter createParameter(Parameter parameter) {
        return parameterRepo.save(parameter);
    }

    @Override
    public Optional<Parameter> getParameterById(Integer id) {
        return parameterRepo.findById(id);
    }

    @Override
    public List<Parameter> getAllParameters() {
        return parameterRepo.findAll();
    }

    @Override
    public Parameter updateParameter(Integer id, Parameter updatedParameter) {
        return parameterRepo.findById(id)
                .map(existingParameter -> {
                    existingParameter.setCode(updatedParameter.getCode());
                    existingParameter.setName(updatedParameter.getName());
                    
                    return parameterRepo.save(existingParameter);
                })
                .orElse(null);
    }

    @Override
    public void deleteParameter(Integer id) {
        parameterRepo.deleteById(id);
    }
}