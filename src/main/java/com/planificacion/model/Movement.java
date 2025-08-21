package com.planificacion.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "movement")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "inventory_unit_id", nullable = false, foreignKey = @ForeignKey(name = "FK_movement_inventory"))
    private InventoryUnit inventoryUnit;

    @ManyToOne
    @JoinColumn(name = "administrative_unit_id", nullable = false, foreignKey = @ForeignKey(name = "FK_movement_unit"))
    private UnidadAdministrativa administrativeUnit;

    @Column(nullable = false)
    private int quantity;

    @Column(name = "movement_type", nullable = false)
    private String type; // "ENTRADA" o "SALIDA"

    @Column(name = "movement_date", nullable = false)
    private LocalDateTime movementDate;
}
