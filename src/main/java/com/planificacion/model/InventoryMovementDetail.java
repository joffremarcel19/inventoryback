package com.planificacion.model;

import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "inventory_movement_detail")
public class InventoryMovementDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "inventory_movement_id", nullable = false, foreignKey = @ForeignKey(name = "FK_movement_detail"))
    private InventoryMovement inventoryMovement;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "FK_movement_detail_product"))
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}