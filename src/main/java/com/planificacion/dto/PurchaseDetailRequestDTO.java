package com.planificacion.dto;

import lombok.Data;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.DecimalMin;

@Data
public class PurchaseDetailRequestDTO {

    @NotNull(message = "Product ID cannot be null")
    private Integer productId;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Unit price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Unit price must be greater than 0")
    private Double unitPrice;
    private Double subtotal;

    private LocalDate expirationDate; // Puede ser nulo
}