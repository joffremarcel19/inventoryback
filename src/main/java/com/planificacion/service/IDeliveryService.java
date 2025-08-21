package com.planificacion.service;

import com.planificacion.dto.DeliveryRequestDTO;
import com.planificacion.model.Delivery;
import java.util.List;

public interface IDeliveryService {
    Delivery register(Delivery d);
    List<Delivery> listAll();
    // ✅ Modifica el tipo de retorno a Delivery
    Delivery registerMultipleDeliveries(DeliveryRequestDTO request);
    // ✅ Agrega esta línea:
    Delivery findById(Integer id);
}