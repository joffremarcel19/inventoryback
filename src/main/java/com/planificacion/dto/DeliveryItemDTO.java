package com.planificacion.dto;

public class DeliveryItemDTO {
    private int inventoryUnitId;
    private int quantity;
    private int productid;


    // Getters y setters
    public int getInventoryUnitId() {
        return inventoryUnitId;
    }

    public void setInventoryUnitId(int inventoryUnitId) {
        this.inventoryUnitId = inventoryUnitId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

}
