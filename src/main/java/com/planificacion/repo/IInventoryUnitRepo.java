package com.planificacion.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planificacion.model.InventoryUnit;
import com.planificacion.model.Product;
import com.planificacion.model.UnidadAdministrativa;

public interface IInventoryUnitRepo extends JpaRepository<InventoryUnit, Integer> {

    @Query("SELECT i FROM InventoryUnit i WHERE LOWER(i.product.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<InventoryUnit> buscarPorNombreProducto(@Param("name") String name);

    // Método para buscar por ID de Unidad Principal (correctly referencing the 'id' field)
    @Query("SELECT iu FROM InventoryUnit iu JOIN iu.unit ua WHERE ua.unidadPrincipal.id = :idUnidadP")
    List<InventoryUnit> findByUnit_UnidadPrincipal_IdUniP(@Param("idUnidadP") Integer idUnidadP);

    // Nuevo método para buscar por Producto y Unidad Administrativa
   /* Optional<InventoryUnit> findByProductAndUnitIn(Product product, List<UnidadAdministrativa> units);*/
    @Query("SELECT iu FROM InventoryUnit iu JOIN iu.unit ua WHERE iu.product = :product AND ua = :unidadAdministrativa")
    Optional<InventoryUnit> findByProductAndUnidadAdministrativa(@Param("product") Product product, @Param("unidadAdministrativa") UnidadAdministrativa unidadAdministrativa);
 // ✅ Este es el método que necesitas declarar para que funcione
    Optional<InventoryUnit> findByProduct_IdAndUnit_IdUniAdm(Integer productId, Integer unidadAdministrativaId);
 // ✅ Agrega esta línea
    List<InventoryUnit> findByUnit_IdUniAdm(Integer idUniAdm);
}