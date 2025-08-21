package com.planificacion.dto;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMin;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryMovementRequestDTO {

    @NotBlank(message = "Movement type cannot be empty")
    private String movementType; // "INCOME" or "OUTCOME"

    @NotNull(message = "User ID cannot be null")
    private Integer userId;

    @NotNull(message = "Administrative unit ID cannot be null")
    private Integer administrativeUnitId;

    private Integer idMov;
    private String documentType;

    @Valid
    @NotNull(message = "Movement details cannot be null")
    private List<InventoryMovementDetailRequestDTO> details;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class InventoryMovementDetailRequestDTO {

        @NotNull(message = "Product ID cannot be null")
        private Integer productId;

        @NotNull(message = "Quantity cannot be null")
        @DecimalMin(value = "0", inclusive = false, message = "Quantity must be greater than 0")
        private Integer quantity;
    }
}