package com.planificacion.dto;

import com.planificacion.model.Purchase;
import com.planificacion.model.PurchaseDetail;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PurchaseReportDTO {

    private String purchaseOrder;
    private LocalDate purchaseDate;
    private String invoiceNumber;
    private String authorizationCode;
    private String status;
    private String observation;

    private String supplierName;
    private String supplierRuc;
    
    private String responsibleUserFullName;
    
    private String administrativeUnitName;
    private Double subtotal;
    private Double subtotalIva;
    private Double subtotalIva0;
    private Double irbp;
    private Double ice;
    private Double discount;
    private Double iva;
    private Double totalAmount;
    private String purchaseType;
    private String proformaNumber;
    private LocalDate proformaDate;
    private String proformaContact;
    private Integer proformaValidity;
    
    private List<PurchaseDetailReportDTO> details;

    public PurchaseReportDTO(Purchase purchase) {
        this.purchaseOrder = purchase.getPurchaseOrder();
        this.purchaseDate = purchase.getPurchaseDate();
        this.invoiceNumber = purchase.getInvoiceNumber();
        this.authorizationCode = purchase.getAuthorizationCode();
        this.status = purchase.getStatus();
        this.observation = purchase.getObservation();

        this.supplierName = purchase.getSupplier().getName();
        this.supplierRuc = purchase.getSupplier().getRuc();
       
        this.subtotal = purchase.getSubtotal();
        this.subtotalIva = purchase.getSubtotalIva();
        this.subtotalIva0 = purchase.getSubtotalIva0();
        this.irbp = purchase.getIrbp();
        this.ice = purchase.getIce();
        this.discount = purchase.getDiscount();
        this.iva = purchase.getIva();
        this.totalAmount = purchase.getTotalAmount();
        this.purchaseType = purchase.getPurchaseType();
        this.proformaNumber = purchase.getProformaNumber();
        this.proformaDate = purchase.getProformaDate();
        this.proformaContact = purchase.getProformaContact();
        this.proformaValidity = purchase.getProformaValidity();
        
        this.details = purchase.getPurchaseDetails().stream()
                .map(PurchaseDetailReportDTO::new)
                .collect(Collectors.toList());
    }

    @Data
    public static class PurchaseDetailReportDTO {
        private String productName;
        private String productCode;
        private Integer quantity;
        private Double unitPrice;
        private Double total;

        public PurchaseDetailReportDTO(PurchaseDetail detail) {
            this.productName = detail.getProduct().getName();
            this.quantity = detail.getQuantity();
            this.unitPrice = detail.getUnitPrice();
            this.total = detail.getSubtotal();
        }
    }
}