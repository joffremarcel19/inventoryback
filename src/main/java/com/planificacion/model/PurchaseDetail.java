package com.planificacion.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "purchase_detail")
public class PurchaseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "purchase_id", nullable = false, foreignKey = @ForeignKey(name = "FK_purchase_detail_purchase"))
    private Purchase purchase;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false, foreignKey = @ForeignKey(name = "FK_purchase_detail_product"))
    private Product product;

    @Column(name = "quantity", nullable = false)
    private  Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private Double unitPrice;

    @Column(name = "subtotal", nullable = false, precision = 10, scale = 2)
    private Double subtotal;

    @Column(name = "expiration_date") // Fecha de Caducidad específica del producto
    private LocalDate expirationDate;
}