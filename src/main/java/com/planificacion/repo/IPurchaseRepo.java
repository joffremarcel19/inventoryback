package com.planificacion.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.planificacion.model.Purchase;

public interface IPurchaseRepo extends IGenericRepo<Purchase, Integer> {

	@Query("SELECT p FROM Purchase p " +
			"LEFT JOIN FETCH p.supplier s " + // Carga el proveedor
			"LEFT JOIN FETCH p.paymentMethod pm " + // Carga el método de pago
		    "JOIN FETCH p.responsibleUser u " +
			"JOIN FETCH p.unidadAdministrativaCompra uac " +
			"LEFT JOIN FETCH p.administrator a " +
		    "JOIN FETCH uac.unidadPrincipal up " +
			"LEFT JOIN FETCH p.purchaseDetails pd " +
			"LEFT JOIN FETCH pd.product prod " +
			"LEFT JOIN FETCH u.unidadAdministrativa ua " +
			"LEFT JOIN FETCH ua.unidadPrincipal upUser " +
			"WHERE p.id = :id")
			Optional<Purchase> findByIdWithAllDetails(@Param("id") Integer id);

	@Query("SELECT DISTINCT p FROM Purchase p " +
			"LEFT JOIN FETCH p.supplier s " + // Carga el proveedor
			"LEFT JOIN FETCH p.paymentMethod pm " + // Carga el método de pago
			"JOIN FETCH p.responsibleUser u " +
			"LEFT JOIN FETCH p.administrator a " +
			"JOIN FETCH p.unidadAdministrativaCompra uac " +
			"JOIN FETCH uac.unidadPrincipal up " +
			"LEFT JOIN FETCH p.purchaseDetails pd " +
			"LEFT JOIN FETCH pd.product prod " +
			"LEFT JOIN FETCH u.unidadAdministrativa ua " +
			"LEFT JOIN FETCH ua.unidadPrincipal upUser ")
			List<Purchase> findAllWithAllDetails();

	@Query("SELECT DISTINCT p FROM Purchase p " +
			"LEFT JOIN FETCH p.supplier s " + // Carga el proveedor
			"LEFT JOIN FETCH p.paymentMethod pm " + // Carga el método de pago
			"JOIN FETCH p.responsibleUser u " +
			"LEFT JOIN FETCH p.administrator a " +
			"JOIN FETCH p.unidadAdministrativaCompra uac " +
			"JOIN FETCH uac.unidadPrincipal up " +
			"LEFT JOIN FETCH p.purchaseDetails pd " +
			"LEFT JOIN FETCH pd.product prod " +
			"LEFT JOIN FETCH u.unidadAdministrativa ua " +
			"LEFT JOIN FETCH ua.unidadPrincipal upUser " +
			"WHERE u.idUsuario = :responsibleUserId")
			List<Purchase> findByResponsibleUserWithAllDetails(@Param("responsibleUserId") Integer responsibleUserId);

	@Query("SELECT DISTINCT p FROM Purchase p " +
			"LEFT JOIN FETCH p.supplier s " +
			"LEFT JOIN FETCH p.paymentMethod pm " +
			"LEFT JOIN FETCH p.responsibleUser ru " +
			"LEFT JOIN FETCH p.administrator a " +
			"LEFT JOIN FETCH p.unidadAdministrativaCompra uac " +
			"LEFT JOIN FETCH p.purchaseDetails pd " +
			"LEFT JOIN FETCH pd.product prod " +
			"WHERE uac.idUniAdm = :unidadAdministrativaId")
			List<Purchase> findByUnidadAdministrativaCompraIdUniAdmWithDetails(@Param("unidadAdministrativaId") Integer unidadAdministrativaId);
			
	 @Query("SELECT DISTINCT p FROM Purchase p " +
			"LEFT JOIN FETCH p.supplier s " +
			"LEFT JOIN FETCH p.paymentMethod pm " +
			"LEFT JOIN FETCH p.responsibleUser ru " +
			"LEFT JOIN FETCH p.administrator a " +
			"LEFT JOIN FETCH p.unidadAdministrativaCompra uac " +
			"LEFT JOIN FETCH uac.unidadPrincipal up " +
			"LEFT JOIN FETCH p.purchaseDetails pd " +
			"LEFT JOIN FETCH pd.product prod " +
			"WHERE up.id = :unidadPrincipalId")
			List<Purchase> findByUnidadPrincipalIdWithDetails(@Param("unidadPrincipalId") Integer unidadPrincipalId);
	 Optional<Purchase> findByInvoiceNumber(String invoiceNumber);
		}