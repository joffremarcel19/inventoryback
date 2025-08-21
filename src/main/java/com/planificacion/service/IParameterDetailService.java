package com.planificacion.service;

import com.planificacion.model.ParameterDetail;
import java.util.List;
import java.util.Optional;

public interface IParameterDetailService {
    ParameterDetail createParameterDetail(ParameterDetail parameterDetail);
    Optional<ParameterDetail> getParameterDetailById(Integer id);
    List<ParameterDetail> getAllParameterDetails();
    ParameterDetail updateParameterDetail(Integer id, ParameterDetail updatedParameterDetail);
    void deleteParameterDetail(Integer id);
    List<ParameterDetail> getParameterDetailsByParameterCode(String parameterCode);
    List<ParameterDetail> getParameterDetailsByParameterId(Integer parameterId);
 // ✅ Nuevo método en la interfaz
    List<ParameterDetail> listByParameterIdAndValue(Integer parameterId, String value);
}