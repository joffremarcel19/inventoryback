package com.planificacion.service.impl;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.planificacion.dto.PurchaseDTO;
import com.planificacion.dto.PurchaseRequestDTO;
import com.planificacion.dto.PurchaseDetailRequestDTO;
import com.planificacion.exception.ModeloNotFoundException;
import com.planificacion.model.ParameterDetail;
import com.planificacion.model.Persona; // Importar la entidad Persona
import com.planificacion.model.Product;
import com.planificacion.model.Purchase;
import com.planificacion.model.PurchaseDetail;
import com.planificacion.model.Supplier;
import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.model.Usuario;
import com.planificacion.repo.IParameterDetailRepo;
import com.planificacion.repo.IPersonaRepo; // ¡NUEVO REPOSITORIO AUTOWIREADO!
import com.planificacion.repo.IProductRepo;
import com.planificacion.repo.IPurchaseDetailRepo;
import com.planificacion.repo.IPurchaseRepo;
import com.planificacion.repo.ISupplierRepo;
import com.planificacion.repo.IUnidadAdministrativaRepo;
import com.planificacion.repo.IUsuarioRepo;
import com.planificacion.service.IInventoryMovementService;
import com.planificacion.service.IPurchaseService;

@Service
public class PurchaseServiceImpl implements IPurchaseService {

    @Autowired
    private IPurchaseRepo purchaseRepo;

    @Autowired
    private IPurchaseDetailRepo purchaseDetailRepo;

    @Autowired
    private ISupplierRepo supplierRepo;

    @Autowired
    private IUsuarioRepo usuarioRepo;

    @Autowired
    private IParameterDetailRepo parameterDetailRepo;

    @Autowired
    private IProductRepo productRepo;

    @Autowired
    private IUnidadAdministrativaRepo unidadAdministrativaRepo;

    @Autowired
    private IPersonaRepo personaRepo; // <-- NUEVO: Repositorio para la persona administradora
    @Autowired
    private IInventoryMovementService inventoryMovementService;

    @Override
    public List<PurchaseDTO> listAllPurchasesWithDetails() throws Exception {
        List<Purchase> purchases = purchaseRepo.findAllWithAllDetails();
        return purchases.stream()
                .map(PurchaseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public PurchaseDTO listPurchaseByIdWithDetails(Integer id) throws Exception {
        Purchase purchase = purchaseRepo.findByIdWithAllDetails(id)
                .orElseThrow(() -> new ModeloNotFoundException("Purchase not found with ID: " + id));
        return new PurchaseDTO(purchase);
    }
    
    

    @Transactional
    @Override
    public Purchase register(PurchaseRequestDTO request) throws Exception {
    	  if (purchaseRepo.findByInvoiceNumber(request.getInvoiceNumber()).isPresent()) {
              throw new IllegalArgumentException("El número de factura '" + request.getInvoiceNumber() + "' ya existe en el sistema.");
          }
        Purchase purchase = new Purchase();

        // Mapeo de campos directos
        purchase.setPurchaseOrder(request.getPurchaseOrder());
        purchase.setPurchaseDate(request.getPurchaseDate() != null ? request.getPurchaseDate() : LocalDate.now());
        purchase.setEntryDate(request.getEntryDate() != null ? request.getEntryDate() : LocalDateTime.now());
        purchase.setInvoiceNumber(request.getInvoiceNumber());
        purchase.setNotes(request.getNotes());
        purchase.setObservation(request.getObservation());
        purchase.setStatus(request.getStatus());
        purchase.setSubtotal(request.getSubtotal());
        purchase.setSubtotalIva(request.getSubtotalIva());
        purchase.setSubtotalIva0(request.getSubtotalIva0());
        purchase.setIrbp(request.getIrbp());
        purchase.setIce(request.getIce());
        purchase.setDiscount(request.getDiscount());
        purchase.setIva(request.getIva());
        purchase.setAuthorizationCode(request.getAuthorizationCode());

        // Mapeo de relaciones, buscando las entidades por ID
        Supplier supplier = supplierRepo.findById(request.getSupplierId())
                .orElseThrow(() -> new ModeloNotFoundException("Supplier not found with ID: " + request.getSupplierId()));
        purchase.setSupplier(supplier);

        ParameterDetail paymentMethod = parameterDetailRepo.findById(request.getPaymentMethodId())
                .orElseThrow(() -> new ModeloNotFoundException("Payment Method not found with ID: " + request.getPaymentMethodId()));
        purchase.setPaymentMethod(paymentMethod);

        Usuario responsibleUser = usuarioRepo.findById(request.getResponsibleUserId())
                .orElseThrow(() -> new ModeloNotFoundException("Responsible User not found with ID: " + request.getResponsibleUserId()));
        purchase.setResponsibleUser(responsibleUser);

        UnidadAdministrativa unidadAdministrativaCompra = unidadAdministrativaRepo.findById(request.getId_unidad_adm())
                .orElseThrow(() -> new ModeloNotFoundException("Unidad Administrativa not found with ID: " + request.getId_unidad_adm()));
        purchase.setUnidadAdministrativaCompra(unidadAdministrativaCompra);

        // --- NUEVA LÓGICA: BUSCAR Y ASIGNAR LA PERSONA ADMINISTRADORA ---
        Persona administrator = personaRepo.findById(request.getId_persona())
                .orElseThrow(() -> new ModeloNotFoundException("Administrator Persona not found with ID: " + request.getId_persona()));
        purchase.setAdministrator(administrator);

        // **Procesar y calcular detalles y subtotal/total antes de guardar**
        double totalCalculated = 0.0;
        purchase.setPurchaseDetails(new ArrayList<>());

        if (request.getItems() != null && !request.getItems().isEmpty()) {
            for (PurchaseRequestDTO.PurchaseItemDTO detailDTO : request.getItems()) {
                PurchaseDetail detail = new PurchaseDetail();
                Product product = productRepo.findById(detailDTO.getProductId())
                        .orElseThrow(() -> new ModeloNotFoundException("Product not found with ID: " + detailDTO.getProductId()));
                detail.setProduct(product);

                detail.setQuantity(detailDTO.getQuantity());
                detail.setUnitPrice(detailDTO.getUnitPrice());
                detail.setExpirationDate(detailDTO.getExpirationDate());

                if (detail.getQuantity() != null && detail.getUnitPrice() != null) {
                    detail.setSubtotal(detail.getQuantity() * detail.getUnitPrice());
                } else {
                    throw new IllegalArgumentException("Quantity and Unit Price cannot be null for a purchase detail. Detail for product ID: " + detailDTO.getProductId());
                }

                purchase.addPurchaseDetail(detail);
                totalCalculated += detail.getSubtotal();
            }
        }
        purchase.setTotalAmount(totalCalculated);
        // 2. ✅ Guardar la compra en la base de datos
        Purchase savedPurchase = purchaseRepo.save(purchase);
     // 3. ✅ **AÑADIR ESTA LÓGICA:** Actualizar el stock para cada producto de la compra
        Integer unidadAdministrativaId = savedPurchase.getUnidadAdministrativaCompra().getIdUniAdm();
        for (PurchaseDetail detail : savedPurchase.getPurchaseDetails()) {
            Integer productId = detail.getProduct().getId();
            Integer quantity = detail.getQuantity();

            // Llamar al servicio de inventario para aumentar el stock
            // NOTA: Asegúrate de que IInventoryUnitService esté autowireado en este servicio.
            //       Si no tienes un servicio de inventario, deberías crearlo.
            //       Tu código ya muestra un IInventoryMovementService, pero la lógica del stock
            //       está en IInventoryUnitService.
            //
            //       Debes tener:
            //       @Autowired
            //       private IInventoryUnitService inventoryUnitService;
            
            // Llama a tu método corregido
            //inventoryUnitService.aumentarStock(productId, unidadAdministrativaId, quantity);
        }

        return purchaseRepo.save(purchase);
    }

    @Transactional
    @Override
    public Purchase update(Integer id, PurchaseRequestDTO request) throws Exception {
        Purchase existingPurchase = purchaseRepo.findByIdWithAllDetails(id)
                .orElseThrow(() -> new ModeloNotFoundException("Purchase not found with ID: " + id));

        // Actualizar campos directos
        existingPurchase.setPurchaseOrder(request.getPurchaseOrder());
        existingPurchase.setPurchaseDate(request.getPurchaseDate());
        existingPurchase.setEntryDate(request.getEntryDate());
        existingPurchase.setInvoiceNumber(request.getInvoiceNumber());
        existingPurchase.setNotes(request.getNotes());
        existingPurchase.setObservation(request.getObservation());
        existingPurchase.setStatus(request.getStatus());
        existingPurchase.setDiscount(request.getDiscount());
        existingPurchase.setIva(request.getIva());
        existingPurchase.setSubtotal(request.getSubtotal());
        existingPurchase.setSubtotalIva(request.getSubtotalIva());
        existingPurchase.setSubtotalIva0(request.getSubtotalIva0());
        existingPurchase.setIrbp(request.getIrbp());
        existingPurchase.setIce(request.getIce());
        existingPurchase.setAuthorizationCode(request.getAuthorizationCode());

        // Actualizar relaciones (solo si el ID en el DTO es diferente o no nulo)
        if (request.getSupplierId() != null && (existingPurchase.getSupplier() == null || !existingPurchase.getSupplier().getId().equals(request.getSupplierId()))) {
            Supplier supplier = supplierRepo.findById(request.getSupplierId())
                    .orElseThrow(() -> new ModeloNotFoundException("Supplier not found with ID: " + request.getSupplierId()));
            existingPurchase.setSupplier(supplier);
        }

        if (request.getPaymentMethodId() != null && (existingPurchase.getPaymentMethod() == null || !existingPurchase.getPaymentMethod().getId().equals(request.getPaymentMethodId()))) {
            ParameterDetail paymentMethod = parameterDetailRepo.findById(request.getPaymentMethodId())
                    .orElseThrow(() -> new ModeloNotFoundException("Payment Method not found with ID: " + request.getPaymentMethodId()));
            existingPurchase.setPaymentMethod(paymentMethod);
        }

        if (request.getResponsibleUserId() != null && (existingPurchase.getResponsibleUser() == null || !existingPurchase.getResponsibleUser().getIdUsuario().equals(request.getResponsibleUserId()))) {
            Usuario responsibleUser = usuarioRepo.findById(request.getResponsibleUserId())
                    .orElseThrow(() -> new ModeloNotFoundException("Responsible User not found with ID: " + request.getResponsibleUserId()));
            existingPurchase.setResponsibleUser(responsibleUser);
        }

        if (request.getId_unidad_adm() != null && (existingPurchase.getUnidadAdministrativaCompra() == null || !(existingPurchase.getUnidadAdministrativaCompra().getIdUniAdm() == request.getId_unidad_adm()))) {
            UnidadAdministrativa unidadAdministrativaCompra = unidadAdministrativaRepo.findById(request.getId_unidad_adm())
                    .orElseThrow(() -> new ModeloNotFoundException("Unidad Administrativa not found with ID: " + request.getId_unidad_adm()));
            existingPurchase.setUnidadAdministrativaCompra(unidadAdministrativaCompra);
        }

        // --- NUEVA LÓGICA: ACTUALIZAR LA PERSONA ADMINISTRADORA ---
        if (request.getId_persona() != null && (existingPurchase.getAdministrator() == null || !(existingPurchase.getAdministrator().getIdPersona() == request.getId_persona()))) {
            Persona administrator = personaRepo.findById(request.getId_persona())
                    .orElseThrow(() -> new ModeloNotFoundException("Administrator Persona not found with ID: " + request.getId_persona()));
            existingPurchase.setAdministrator(administrator);
        }

        existingPurchase.getPurchaseDetails().clear();

        double totalCalculated = 0.0;
        if (request.getItems() != null && !request.getItems().isEmpty()) {
            for (PurchaseRequestDTO.PurchaseItemDTO detailDTO : request.getItems()) {
                PurchaseDetail newDetail = new PurchaseDetail();

                Product product = productRepo.findById(detailDTO.getProductId())
                        .orElseThrow(() -> new ModeloNotFoundException("Product not found with ID: " + detailDTO.getProductId()));
                newDetail.setProduct(product);

                newDetail.setQuantity(detailDTO.getQuantity());
                newDetail.setUnitPrice(detailDTO.getUnitPrice());
                newDetail.setExpirationDate(detailDTO.getExpirationDate());

                if (newDetail.getQuantity() != null && newDetail.getUnitPrice() != null) {
                    newDetail.setSubtotal(newDetail.getQuantity() * newDetail.getUnitPrice());
                } else {
                    throw new IllegalArgumentException("Quantity and Unit Price cannot be null for a purchase detail. Detail for product ID: " + detailDTO.getProductId());
                }

                existingPurchase.addPurchaseDetail(newDetail);
                totalCalculated += newDetail.getSubtotal();
            }
        }
        existingPurchase.setTotalAmount(totalCalculated);

        return purchaseRepo.save(existingPurchase);
    }

    @Override
    public void delete(Integer id) throws Exception {
        if (!purchaseRepo.existsById(id)) {
            throw new ModeloNotFoundException("Purchase not found with ID: " + id);
        }
        purchaseRepo.deleteById(id);
    }

    @Override
    public List<PurchaseDTO> getPurchasesByResponsibleUser(Integer responsibleUserId) throws Exception {
        List<Purchase> purchases = purchaseRepo.findByResponsibleUserWithAllDetails(responsibleUserId);
        return purchases.stream()
                .map(PurchaseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PurchaseDTO> getPurchasesByUnidadAdministrativa(Integer unidadAdministrativaId) throws Exception {
        return purchaseRepo.findByUnidadAdministrativaCompraIdUniAdmWithDetails(unidadAdministrativaId).stream()
                .map(PurchaseDTO::new)
                .collect(Collectors.toList());
    }
    @Override
    public List<PurchaseDTO> getPurchasesByUnidadPrincipal(Integer unidadPrincipalId) throws Exception {
        return purchaseRepo.findByUnidadPrincipalIdWithDetails(unidadPrincipalId).stream()
                .map(PurchaseDTO::new)
                .collect(Collectors.toList());
    }
    @Override
    public Purchase findById(Integer id) throws Exception {
        return purchaseRepo.findById(id)
            .orElseThrow(() -> new ModeloNotFoundException("Purchase not found with ID: " + id));
    }
 // En PurchaseServiceImpl.java -> Método updateStatus
    @Override
    @Transactional
    public void updateStatus(Integer id, String status) throws Exception {
        Purchase existingPurchase = purchaseRepo.findById(id)
            .orElseThrow(() -> new ModeloNotFoundException("Purchase not found with ID: " + id));

        String oldStatus = existingPurchase.getStatus();
        
        // Asignar el nuevo estado, pero no guardar todavía
        existingPurchase.setStatus(status);
        
        Purchase updatedPurchase = purchaseRepo.save(existingPurchase);

        // --- LOGUEO DETALLADO PARA DEPURACIÓN ---
        String newStatusValue = updatedPurchase.getStatus();
        
        System.out.println("---------------- DEBUG INICIO ----------------");
        System.out.printf("Estado Antiguo: '%s'\n", oldStatus);
        System.out.printf("Estado Nuevo: '%s'\n", newStatusValue);
        
        boolean statusChanged = !oldStatus.equals(newStatusValue);
        boolean isNewStatusIngreso = "INGRESO".equals(newStatusValue);
        boolean finalCondition = statusChanged && isNewStatusIngreso;
        
        System.out.printf("¿Cambio el estado? %s\n", statusChanged);
        System.out.printf("¿El nuevo estado es 'INGRESO'? %s\n", isNewStatusIngreso);
        System.out.printf("Resultado final de la condición: %s\n", finalCondition);
        System.out.println("---------------- DEBUG FIN ----------------");

        if (finalCondition) {
            inventoryMovementService.createMovementFromPurchase(updatedPurchase);
        }
    }
}