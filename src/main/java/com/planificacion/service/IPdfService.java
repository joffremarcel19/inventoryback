package com.planificacion.service;

import com.planificacion.dto.PurchaseReportDTO;
import java.io.ByteArrayInputStream;

public interface IPdfService {
    ByteArrayInputStream generatePurchaseReport(PurchaseReportDTO reportData) throws Exception;
}