package com.planificacion.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.planificacion.model.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InventoryMovementDTO {

    private Integer id;
    private String movementType;
    private LocalDateTime movementDate;
    
    private UsuarioDTO user;
    private UnidadAdministrativaDTO administrativeUnit;
    
    private Integer idMov;
    private String documentType;
    private List<InventoryMovementDetailDTO> details;

    public InventoryMovementDTO(InventoryMovement movement) {
        this.id = movement.getId();
        this.movementType = movement.getMovementType();
        this.movementDate = movement.getMovementDate();
        this.user = new UsuarioDTO(movement.getUser());
        this.administrativeUnit = new UnidadAdministrativaDTO(movement.getAdministrativeUnit());
        this.idMov = movement.getIdMov();
        this.documentType = movement.getDocumentType();
        this.details = movement.getDetails().stream()
                .map(InventoryMovementDetailDTO::new)
                .collect(Collectors.toList());
    }
    
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UsuarioDTO {
        private Integer idUsuario;
        private String username;
        private String cedula;
        private String nombres;
        private String apellidos;
        private String email;

        public UsuarioDTO(Usuario usuario) {
            this.idUsuario = usuario.getIdUsuario();
            this.username = usuario.getUsername();
            this.cedula = usuario.getCedula();
            this.nombres = usuario.getNombres();
            this.apellidos = usuario.getApellidos();
            this.email = usuario.getEmail();
        }
    }
    
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UnidadAdministrativaDTO {
        private Integer idUniAdm;
        private String nombre;
        private String codigo;

        public UnidadAdministrativaDTO(UnidadAdministrativa unidad) {
            this.idUniAdm = unidad.getIdUniAdm();
            this.nombre = unidad.getNombreUni(); // <--- Corregido: ahora usa getNombre()
        }
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class InventoryMovementDetailDTO {
        private Integer id;
        
        private ProductDTO product;
        private Integer quantity;

        public InventoryMovementDetailDTO(InventoryMovementDetail detail) {
            this.id = detail.getId();
            this.product = new ProductDTO(detail.getProduct());
            this.quantity = detail.getQuantity();
        }
    }
    
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ProductDTO {
        private Integer id;
        private String name;
        private String code;
        private String description;

        public ProductDTO(Product product) {
            this.id = product.getId();
            this.name = product.getName();
            this.code = product.getPartNumber();
            this.description = product.getDescription();
        }
    }
}