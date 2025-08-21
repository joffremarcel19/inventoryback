package com.planificacion.repo;

import com.planificacion.model.PurchaseDetail;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IPurchaseDetailRepo extends IGenericRepo<PurchaseDetail, Integer> {
    // Aquí puedes agregar métodos de consulta específicos para PurchaseDetail si los necesitas
    // Por ejemplo:
    List<PurchaseDetail> findByPurchase_Id(Integer purchaseId);
    List<PurchaseDetail> findByProduct_Id(Integer productId);

    // Ejemplo de consulta para encontrar detalles de compra por rango de fechas de caducidad
    @Query("SELECT pd FROM PurchaseDetail pd WHERE pd.expirationDate BETWEEN :startDate AND :endDate")
    List<PurchaseDetail> findByExpirationDateBetween(@Param("startDate") java.time.LocalDate startDate, @Param("endDate") java.time.LocalDate endDate);
}