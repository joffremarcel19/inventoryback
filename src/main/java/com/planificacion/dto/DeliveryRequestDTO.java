package com.planificacion.dto;

import java.time.LocalDateTime;
import java.util.List;

public class DeliveryRequestDTO {
    private int administrativeUnitId;
    private int personId; // Nuevo campo para el ID de la persona que recibe
    private int userId; // Nuevo campo para el ID del usuario que realiza la entrega
    private LocalDateTime deliveryDate;
    private String description; // Renombrado para coincidir con la entidad Delivery
    private List<DeliveryItemDTO> items;
    private Integer deliveryTypeDetailId;

    // Getters y setters

    public int getAdministrativeUnitId() {
        return administrativeUnitId;
    }

    public void setAdministrativeUnitId(int administrativeUnitId) {
        this.administrativeUnitId = administrativeUnitId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DeliveryItemDTO> getItems() {
        return items;
    }

    public void setItems(List<DeliveryItemDTO> items) {
        this.items = items;
    }

	public Integer getDeliveryTypeDetailId() {
		return deliveryTypeDetailId;
	}

	public void setDeliveryTypeDetailId(Integer deliveryTypeDetailId) {
		this.deliveryTypeDetailId = deliveryTypeDetailId;
	}
    
}