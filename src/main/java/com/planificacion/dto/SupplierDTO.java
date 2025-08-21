package com.planificacion.dto;

import com.planificacion.model.Supplier;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class SupplierDTO {
    private Integer id;
    private String name;
    private String legalRepresentative;
    private LocalDateTime creationDate;
    private String status;
    private String ruc;
    private String businessName;
    private String companyName;
    private String phoneNumber;
    private String cellNumber;
    private String email;
    private String address;

    public SupplierDTO(Supplier supplier) {
        this.id = supplier.getId();
        this.name = supplier.getName();
        this.legalRepresentative = supplier.getLegalRepresentative();
        this.creationDate = supplier.getCreationDate();
        this.status = supplier.getStatus();
        this.ruc = supplier.getRuc();
        this.businessName = supplier.getBusinessName();
        this.companyName = supplier.getCompanyName();
        this.phoneNumber = supplier.getPhoneNumber();
        this.cellNumber = supplier.getCellNumber();
        this.email = supplier.getEmail();
        this.address = supplier.getAddress();
    }
}