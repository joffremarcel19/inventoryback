package com.planificacion.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;
import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseRequestDTO {

    private String purchaseOrder;
    private LocalDate purchaseDate;
    private LocalDateTime entryDate;

    @NotBlank(message = "Invoice number cannot be empty")
    private String invoiceNumber;
    private Double totalAmount;
    private String notes;
    private String observation;
    private String status;
    private Double subtotal;
    private Double subtotalIva;
    private Double subtotalIva0;
    private Double irbp;
    private Double ice;
    private Double discount;
    private Double iva;
    private String authorizationCode;
    private String purchaseType;
    private String proformaNumber;
    private LocalDate proformaDate;
    private String proformaContact;
    private Integer proformaValidity;

    @NotNull(message = "Payment method ID cannot be null")
    private Integer paymentMethodId;
    @NotNull(message = "Supplier ID cannot be null")
    private Integer supplierId;
    @NotNull(message = "Responsible user ID cannot be null")
    private Integer responsibleUserId;
    @NotNull(message = "Unidad Administrativa ID cannot be null")
    private Integer id_unidad_adm;

    // --- CAMBIO CLAVE: CAMPO PARA EL ID DE LA PERSONA ADMINISTRADORA ---
    @NotNull(message = "Administrator Persona ID cannot be null")
    private Integer id_persona;

    @Valid
    @NotNull(message = "Purchase items cannot be null")
    private List<PurchaseItemDTO> items;

    // DTO anidado para los detalles, simplificado para mayor claridad
    @Data
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class PurchaseItemDTO {
        @NotNull(message = "Product ID cannot be null")
        private Integer productId;
        @NotNull(message = "Quantity cannot be null")
        @DecimalMin(value = "0", inclusive = false, message = "Quantity must be greater than 0")
        private Integer quantity;
        @NotNull(message = "Unit price cannot be null")
        @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
        private Double unitPrice;
        private LocalDate expirationDate;
    }
}