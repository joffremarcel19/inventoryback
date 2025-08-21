package com.planificacion.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "delivery_detail")
public class DeliveryDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "delivery_id", nullable = false, foreignKey = @ForeignKey(name = "FK_delivery_detail"))
    private Delivery delivery; // Referencia a la entrega principal
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "FK_product_detail"))
    private Product product; // Referencia a la entrega principal

    @Column(nullable = false)
    private int quantity; // Cantidad entregada de un producto específico
   

    @ManyToOne
    @JoinColumn(name = "inventory_unit_id", nullable = false, foreignKey = @ForeignKey(name = "FK_delivery_inventory_unit"))
    private InventoryUnit inventoryUnit; // Referencia a la unidad de inventario (producto específico)
}