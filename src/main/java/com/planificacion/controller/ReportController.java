package com.planificacion.controller;

import com.planificacion.dto.DeliveryReportDTO;
import com.planificacion.dto.PurchaseReportDTO;
import com.planificacion.exception.ModeloNotFoundException;
import com.planificacion.model.Delivery;
import com.planificacion.model.Purchase;
import com.planificacion.service.IDeliveryService;
import com.planificacion.service.IPdfService;
import com.planificacion.service.IPurchaseService; // Asumiendo que ya tienes este servicio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private IPurchaseService purchaseService;

    @Autowired
    private IPdfService pdfService;
    
    @Autowired
    private IDeliveryService deliveryService;

    @GetMapping("/purchase/{id}/pdf")
    public ResponseEntity<InputStreamResource> generatePurchaseReport(@PathVariable Integer id) throws Exception {
        
        // CORREGIMOS ESTA LÍNEA para usar el nuevo método findById
        Purchase purchase = purchaseService.findById(id);
        if (purchase == null) {
            throw new ModeloNotFoundException("Purchase not found with ID: " + id);
        }
        
        PurchaseReportDTO reportData = new PurchaseReportDTO(purchase);
        ByteArrayInputStream bis = pdfService.generatePurchaseReport(reportData);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=purchase_report.pdf");
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
    
    @GetMapping("/delivery/{id}/pdf")
    public ResponseEntity<InputStreamResource> generateDeliveryReport(@PathVariable Integer id) throws Exception {
        Delivery delivery = deliveryService.findById(id);
        if (delivery == null) {
            throw new ModeloNotFoundException("Delivery not found with ID: " + id);
        }
        
        DeliveryReportDTO reportData = new DeliveryReportDTO(delivery);
        ByteArrayInputStream bis = pdfService.generateDeliveryReport(reportData);
        
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=delivery_report.pdf");
        
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
