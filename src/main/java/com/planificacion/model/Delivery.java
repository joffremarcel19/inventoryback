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
    private Persona person; 

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_delivery_usuario"))
    private Usuario user; 
    @ManyToOne
    @JoinColumn(name = "delivery_type_detail_id", nullable = false, foreignKey = @ForeignKey(name = "FK_delivery_parameter_detail"))
    private ParameterDetail deliveryTypeDetail;

    @Column(name = "delivery_date", nullable = false)
    private LocalDateTime deliveryDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description; 
 
    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryDetail> deliveryDetails;
    
}