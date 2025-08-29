package com.planificacion.dto;

import com.planificacion.model.Delivery;
import com.planificacion.model.DeliveryDetail;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class DeliveryReportDTO {

    private int id;
    private String administrativeUnitName;
    private LocalDateTime deliveryDate;
    private String receivedByPersonFullName;
    private String deliveredByUsername;

    private List<DeliveryDetailReportDTO> details;

    public DeliveryReportDTO(Delivery delivery) {
        this.id = delivery.getId();
        this.deliveryDate = delivery.getDeliveryDate();
        
        // Asume que la entidad Persona tiene un campo para el nombre completo
        this.receivedByPersonFullName = delivery.getPerson().getNombres()+ " " + delivery.getPerson().getApellidos();
        
        // Obtiene el username del Usuario que realiza la entrega
        this.deliveredByUsername = delivery.getUser().getNombres()+ " " + delivery.getUser().getApellidos();
        
        // Asume que la persona está asociada a una unidad administrativa
        this.administrativeUnitName = delivery.getPerson().getUnidadAdministrativa().getNombreUni();

        this.details = delivery.getDeliveryDetails().stream()
                .map(DeliveryDetailReportDTO::new)
                .collect(Collectors.toList());
    }

    // La clase anidada para los detalles de la entrega
    @Data
    public static class DeliveryDetailReportDTO {
        private String productName;
        private String productBrand;
        private String productModel;
        private String productColor;
        private String productPartNumber;
        private Integer quantity;

        public DeliveryDetailReportDTO(DeliveryDetail detail) {
            this.productName = detail.getInventoryUnit().getProduct().getName();
            this.productBrand = detail.getInventoryUnit().getProduct().getBrand();
            this.productModel = detail.getInventoryUnit().getProduct().getModel();
            this.productColor = detail.getInventoryUnit().getProduct().getColor();
            this.productPartNumber = detail.getInventoryUnit().getProduct().getPartNumber();
            this.quantity = detail.getQuantity();
        }
    }
}