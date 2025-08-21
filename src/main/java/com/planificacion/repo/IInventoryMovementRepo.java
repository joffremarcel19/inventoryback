package com.planificacion.repo;

import com.planificacion.model.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IInventoryMovementRepo extends JpaRepository<InventoryMovement, Integer> {
}