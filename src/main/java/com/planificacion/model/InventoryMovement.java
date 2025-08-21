package com.planificacion.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
@Entity
@Table(name = "inventory_movement")
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "movement_type", nullable = false)
    private String movementType; // "INCOME" o "OUTCOME"

    @Column(name = "movement_date", nullable = false)
    private LocalDateTime movementDate;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_movement_user"))
    private Usuario user;
    
    @ManyToOne
    @JoinColumn(name = "administrative_unit_id", nullable = false, foreignKey = @ForeignKey(name = "FK_movement_unit"))
    private UnidadAdministrativa administrativeUnit;

    @Column(name = "id_mov")
    private Integer idMov;

    @Column(name = "document_type")
    private String documentType; // "PURCHASE" o "DELIVERY"
    
    @OneToMany(mappedBy = "inventoryMovement", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<InventoryMovementDetail> details = new ArrayList<>();
    
    public void addDetail(InventoryMovementDetail detail) {
        this.details.add(detail);
        detail.setInventoryMovement(this);
    }
}