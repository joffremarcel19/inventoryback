package com.planificacion.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false, foreignKey = @ForeignKey(name = "FK_delivery_persona"))
    private Persona person; // Relación con la persona que recibe

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_delivery_usuario"))
    private Usuario user; // Relación con el usuario que realiza la entrega
 // En tu Delivery.java
    @ManyToOne
    @JoinColumn(name = "delivery_type_detail_id", nullable = false, foreignKey = @ForeignKey(name = "FK_delivery_parameter_detail"))
    private ParameterDetail deliveryTypeDetail;

    @Column(name = "delivery_date", nullable = false)
    private LocalDateTime deliveryDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // Descripción de la entrega
    

    // Relación OneToMany con DeliveryDetail
    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryDetail> deliveryDetails;
    
}