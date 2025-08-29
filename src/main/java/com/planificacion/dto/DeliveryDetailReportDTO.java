package com.planificacion.dto;

import com.planificacion.model.DeliveryDetail;
import lombok.Data;

@Data
public class DeliveryDetailReportDTO {
    private String productName;
    private String productBrand;
    private String productModel;
    private String productColor;
    private String productPartNumber;
    private Integer quantity;

    public DeliveryDetailReportDTO(DeliveryDetail detail) {
        // Navegamos desde DeliveryDetail -> InventoryUnit -> Product
        this.productName = detail.getInventoryUnit().getProduct().getName();
        this.productBrand = detail.getInventoryUnit().getProduct().getBrand();
        this.productModel = detail.getInventoryUnit().getProduct().getModel();
        this.productColor = detail.getInventoryUnit().getProduct().getColor();
        this.productPartNumber = detail.getInventoryUnit().getProduct().getPartNumber();
        this.quantity = detail.getQuantity();
    }
}