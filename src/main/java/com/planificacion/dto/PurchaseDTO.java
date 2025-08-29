package com.planificacion.dto;

import com.planificacion.model.Purchase;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
// import lombok.Data;
// @Data
public class PurchaseDTO {
    private Integer id;
    private String purchaseOrder;
    private LocalDate purchaseDate;
    private Double totalAmount;
    private String invoiceNumber;
    private String status;
    private String observation;
    private String notes;
    private SupplierDTO supplier;
    private String paymentMethod;
    private PersonaDTO administrator;
    private LocalDateTime entryDate;
    private Double subtotal;
    private Double subtotalIva;
    private Double subtotalIva0;
    private Double irbp;
    private Double ice;
    private Double discount;
    private Double iva;
    private String authorizationCode;
    
    // ✅ Nuevo campo: Tipo de compra como String
    private String purchaseType;
    private LocalDate proformaDate;
    private String proformaContact;
    private Integer proformaValidity;

    // ✅ Nuevo campo: Número de proforma
    private String proformaNumber;
    
    private ResponsibleUserDTO responsibleUser; // DTO para el usuario responsable
    private UnidadAdministrativaDTO unidadAdministrativaCompra;
    private List<PurchaseDetailSimpleDTO> details; // DTO simplificado para los detalles

    public PurchaseDTO(Purchase purchase) {
        this.id = purchase.getId();
        this.purchaseOrder = purchase.getPurchaseOrder();
        this.purchaseDate = purchase.getPurchaseDate();
        this.totalAmount = purchase.getTotalAmount();
        this.invoiceNumber = purchase.getInvoiceNumber();
        this.status = purchase.getStatus();
        this.observation = purchase.getObservation();
        this.notes = purchase.getNotes();
        this.entryDate = purchase.getEntryDate();
        this.subtotal = purchase.getSubtotal();
        this.subtotalIva = purchase.getSubtotalIva();
        this.subtotalIva0 = purchase.getSubtotalIva0();
        this.irbp = purchase.getIrbp();
        this.ice = purchase.getIce();
        this.discount = purchase.getDiscount();
        this.iva = purchase.getIva();
        this.authorizationCode = purchase.getAuthorizationCode();
        this.purchaseType = purchase.getPurchaseType();
        this.proformaNumber = purchase.getProformaNumber();
        this.proformaDate = purchase.getProformaDate();
        this.proformaContact = purchase.getProformaContact();
        this.proformaValidity = purchase.getProformaValidity();
     // --- MAPEANDO LOS NUEVOS CAMPOS ---
        if (purchase.getSupplier() != null) {
            this.supplier = new SupplierDTO(purchase.getSupplier());
        }
        if (purchase.getAdministrator() != null) {
            this.administrator = new PersonaDTO(purchase.getAdministrator());
        }

        // Mapea el usuario responsable si existe
        if (purchase.getResponsibleUser() != null) {
            this.responsibleUser = new ResponsibleUserDTO(purchase.getResponsibleUser());
        }

        // ¡¡¡Mapear la nueva relación directa a UnidadAdministrativa de la Compra!!!
        // Asegúrate de que el getter en la entidad se llama getUnidadAdministrativaCompra()
        if (purchase.getUnidadAdministrativaCompra() != null) {
            this.unidadAdministrativaCompra = new UnidadAdministrativaDTO(purchase.getUnidadAdministrativaCompra());
        } else {
            this.unidadAdministrativaCompra = null; // O un DTO predeterminado si lo prefieres
        }

        if (purchase.getPurchaseDetails() != null) {
            this.details = purchase.getPurchaseDetails().stream()
                                .map(PurchaseDetailSimpleDTO::new)
                                .collect(Collectors.toList());
        } else {
            this.details = List.of(); // Devuelve una lista vacía si no hay detalles
        }
    }

    public String getPurchaseType() {
		return purchaseType;
	}

	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}

	public String getProformaNumber() {
		return proformaNumber;
	}

	public void setProformaNumber(String proformaNumber) {
		this.proformaNumber = proformaNumber;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    

    public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}
	

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
    // --- Agrega los getters y setters para los nuevos campos si no usas Lombok ---
    public SupplierDTO getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierDTO supplier) {
        this.supplier = supplier;
    }

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PersonaDTO getAdministrator() {
		return administrator;
	}

	public void setAdministrator(PersonaDTO administrator) {
		this.administrator = administrator;
	}

	public LocalDateTime getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDateTime entryDate) {
		this.entryDate = entryDate;
	}

	public ResponsibleUserDTO getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(ResponsibleUserDTO responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public List<PurchaseDetailSimpleDTO> getDetails() {
        return details;
    }

    public void setDetails(List<PurchaseDetailSimpleDTO> details) {
        this.details = details;
    }
    // Getter y Setter para la nueva propiedad unidadAdministrativaCompra
    public UnidadAdministrativaDTO getUnidadAdministrativaCompra() {
        return unidadAdministrativaCompra;
    }

    public void setUnidadAdministrativaCompra(UnidadAdministrativaDTO unidadAdministrativaCompra) {
        this.unidadAdministrativaCompra = unidadAdministrativaCompra;
    }

	public Double getSubtotalIva() {
		return subtotalIva;
	}

	public void setSubtotalIva(Double subtotalIva) {
		this.subtotalIva = subtotalIva;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public String getAuthorizationCode() {
		return authorizationCode;
	}

	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}

	public Double getSubtotalIva0() {
		return subtotalIva0;
	}

	public void setSubtotalIva0(Double subtotalIva0) {
		this.subtotalIva0 = subtotalIva0;
	}

	public Double getIrbp() {
		return irbp;
	}

	public void setIrbp(Double irbp) {
		this.irbp = irbp;
	}

	public Double getIce() {
		return ice;
	}

	public void setIce(Double ice) {
		this.ice = ice;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public LocalDate getProformaDate() {
		return proformaDate;
	}

	public void setProformaDate(LocalDate proformaDate) {
		this.proformaDate = proformaDate;
	}

	public String getProformaContact() {
		return proformaContact;
	}

	public void setProformaContact(String proformaContact) {
		this.proformaContact = proformaContact;
	}

	public Integer getProformaValidity() {
		return proformaValidity;
	}

	public void setProformaValidity(Integer proformaValidity) {
		this.proformaValidity = proformaValidity;
	}
	
    

}