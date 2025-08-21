package com.planificacion.service.impl;

import com.planificacion.model.Parameter;
import com.planificacion.model.ParameterDetail;
import com.planificacion.repo.IParameterDetailRepo;
import com.planificacion.service.IParameterDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParameterDetailServiceImpl implements IParameterDetailService {

    @Autowired
    private IParameterDetailRepo parameterDetailRepo;

     @Override
    public ParameterDetail createParameterDetail(ParameterDetail parameterDetail) {
        return parameterDetailRepo.save(parameterDetail);
    }

    @Override
    public Optional<ParameterDetail> getParameterDetailById(Integer id) {
        return parameterDetailRepo.findById(id);
    }

    @Override
    public List<ParameterDetail> getAllParameterDetails() {
        return parameterDetailRepo.findAll();
    }

    @Override
    public ParameterDetail updateParameterDetail(Integer id, ParameterDetail updatedParameterDetail) {
        return parameterDetailRepo.findById(id)
                .map(existingDetail -> {
                    existingDetail.setName(updatedParameterDetail.getName());
                    existingDetail.setStatus(updatedParameterDetail.getStatus());
                    existingDetail.setValue(updatedParameterDetail.getValue());
                    return parameterDetailRepo.save(existingDetail);
                })
                .orElse(null);
    }

    @Override
    public void deleteParameterDetail(Integer id) {
        parameterDetailRepo.deleteById(id);
    }

    @Override
    public List<ParameterDetail> getParameterDetailsByParameterCode(String parameterCode) {
        return parameterDetailRepo.findAll().stream()
                .filter(detail -> detail.getParameter().getCode().equals(parameterCode))
                .collect(Collectors.toList());
    }
    @Override
    public List<ParameterDetail> getParameterDetailsByParameterId(Integer parameterId) {
        return parameterDetailRepo.findByParameterId(parameterId);
    }
    // ✅ Implementación del nuevo método
    @Override
    public List<ParameterDetail> listByParameterIdAndValue(Integer parameterId, String value) {
        return parameterDetailRepo.findByParameter_IdAndValue(parameterId, value);
    }
}