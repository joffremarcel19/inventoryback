package com.planificacion.service;

import com.planificacion.dto.DeliveryReportDTO;
import com.planificacion.dto.PurchaseReportDTO;
import java.io.ByteArrayInputStream;

public interface IPdfService {
    ByteArrayInputStream generatePurchaseReport(PurchaseReportDTO reportData) throws Exception;
    ByteArrayInputStream generateDeliveryReport(DeliveryReportDTO reportData) throws Exception;
    
}