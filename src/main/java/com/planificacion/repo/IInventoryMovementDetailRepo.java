package com.planificacion.repo;

import com.planificacion.model.InventoryMovementDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInventoryMovementDetailRepo extends JpaRepository<InventoryMovementDetail, Integer> {
}