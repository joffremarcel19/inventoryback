package com.planificacion.repo;

import com.planificacion.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IDeliveryRepo extends JpaRepository<Delivery, Integer> {

    //@Query("SELECT d FROM Delivery d WHERE d.administrativeUnit.idUniAdm = :unitId")
    //List<Delivery> findByUnitId(@Param("unitId") int unitId);
	// ✅ Consulta para cargar la entrega y todos sus detalles
    @Query("SELECT d FROM Delivery d JOIN FETCH d.deliveryDetails dd WHERE d.id = :id")
    Optional<Delivery> findByIdWithDetails(@Param("id") Integer id);

    @Query("SELECT dd.delivery FROM DeliveryDetail dd WHERE dd.inventoryUnit.product.id = :productId")
    List<Delivery> findByProductId(@Param("productId") int productId);
}