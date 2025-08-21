package com.planificacion.dto;

import com.planificacion.model.PurchaseDetail; // ¡Importa tu entidad PurchaseDetail real!
import java.time.LocalDate;

// Puedes añadir Lombok si lo usas en tu proyecto
// import lombok.Data;
// @Data
public class PurchaseDetailSimpleDTO {
    private Integer id; // ID del detalle de compra (útil al mostrar)
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;
    private LocalDate expirationDate;
    private ProductDTO product;

    /**
     * Constructor que mapea una entidad PurchaseDetail a un PurchaseDetailSimpleDTO.
     * @param detail La entidad PurchaseDetail de la que se obtendrán los datos.
     */
    public PurchaseDetailSimpleDTO(PurchaseDetail detail) {
        this.id = detail.getId();
        this.quantity = detail.getQuantity();
        this.unitPrice = detail.getUnitPrice();
        this.subtotal = detail.getSubtotal();
        this.expirationDate = detail.getExpirationDate();

        // Mapea la entidad Product a ProductDTO si la relación existe
        if (detail.getProduct() != null) {
            this.product = new ProductDTO(detail.getProduct());
        }
    }

    // --- Getters y Setters ---
    // Si no estás usando Lombok (@Data), necesitarás estos getters y setters.
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    // --- ¡AÑADE ESTOS MÉTODOS! ---
    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }
}