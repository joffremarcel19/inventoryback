package com.planificacion.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "purchase")
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "purchase_order") // Orden de Compra
    private String purchaseOrder;
    
    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "entry_date") // Fecha de Ingreso
    private LocalDateTime entryDate;

    @Column(name = "invoice_number", nullable = false, unique = true)
    private String invoiceNumber;
    @Column(name = "authorization_code", nullable = false, unique = true)
    private String authorizationCode;

    @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
    private Double totalAmount;
    @Column(name = "notes", nullable = true)
    private String notes;
    @Column(name = "subtotal", precision = 10, scale = 2)
    private Double subtotal;
    @Column(name = "subtotalIva", precision = 10, scale = 2)
    private Double subtotalIva;
    @Column(name = "subtotalIva0", precision = 10, scale = 2)
    private Double subtotalIva0;
    @Column(name = "irbp", precision = 10, scale = 2)
    private Double irbp;
    @Column(name = "ice", precision = 10, scale = 2)
    private Double ice;

    @Column(name = "discount", precision = 10, scale = 2)
    private Double discount;

    @Column(name = "iva", precision = 10, scale = 2)
    private Double iva;
 // Relación con ParameterDetail para el método de pago (metodo_pago)
    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false, foreignKey = @ForeignKey(name = "FK_purchase_payment_method"))
    private ParameterDetail paymentMethod; // metodo_pago

    @Column(name = "observation", length = 500) // observación, puede ser nula
    private String observation;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false, foreignKey = @ForeignKey(name = "FK_purchase_supplier"))
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "responsible_user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_purchase_responsible_user"))
    private Usuario responsibleUser; // responsable (id_usuario)
    @ManyToOne
    @JoinColumn(name = "id_unidad_adm", nullable = false, foreignKey = @ForeignKey(name = "FK_purchase_unidad_administrativa"))
    private UnidadAdministrativa unidadAdministrativaCompra;
    @ManyToOne
    @JoinColumn(name = "administrator_id", referencedColumnName = "idPersona", nullable = false, foreignKey = @ForeignKey(name = "FK_purchase_administrator_persona"))
    private Persona administrator;

    @Column(name = "status", nullable = false, length = 50) // estado (ej. "PENDING", "COMPLETED", "CANCELLED")
    private String status;
    
    @Column(name = "purchase_type", nullable = true)
    private String purchaseType;

    // ✅ Nuevo campo: Número de Proforma (no obligatorio)
    @Column(name = "proforma_number", nullable = true)
    private String proformaNumber;
    
    @Column(name = "proforma_date", nullable = true)
    private LocalDate proformaDate;

    @Column(name = "proforma_contact", nullable = true)
    private String proformaContact;

    @Column(name = "proforma_validity", nullable = true)
    private Integer proformaValidity;
    
    @JsonIgnore
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PurchaseDetail> purchaseDetails;
 // --- NEW METHODS TO ADD ---

    // Method to add a PurchaseDetail and manage the bidirectional relationship
    public void addPurchaseDetail(PurchaseDetail detail) {
        if (purchaseDetails == null) {
            purchaseDetails = new ArrayList<>();
        }
        purchaseDetails.add(detail);
        detail.setPurchase(this); // Set the back-reference
    }

    // Method to remove a PurchaseDetail and manage the bidirectional relationship
    public void removePurchaseDetail(PurchaseDetail detail) {
        if (purchaseDetails != null) {
            purchaseDetails.remove(detail);
            detail.setPurchase(null); // Remove the back-reference
        }
    }
}