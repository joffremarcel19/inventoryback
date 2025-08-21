package com.planificacion.repo;

import com.planificacion.model.DeliveryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDeliveryDetailRepo extends JpaRepository<DeliveryDetail, Integer> {
    // Puedes añadir métodos de consulta personalizados si los necesitas
}