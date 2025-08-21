package com.planificacion.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.planificacion.dto.PurchaseReportDTO;
import com.planificacion.dto.PurchaseReportDTO.PurchaseDetailReportDTO;
import com.planificacion.service.IPdfService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;

@Service
public class PdfServiceImpl implements IPdfService {

    @Override
    public ByteArrayInputStream generatePurchaseReport(PurchaseReportDTO reportData) throws Exception {
        
        Document document = new Document(PageSize.A4, 30, 30, 30, 30);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        PdfWriter.getInstance(document, out);
        document.open();

        // Logo
        try {
        	Image logo = Image.getInstance(getClass().getResource("/static/imagenes/logo.png"));
            logo.scaleAbsolute(100f, 50f);
            logo.setAlignment(Image.ALIGN_LEFT);
            document.add(logo);
        } catch (Exception e) {
            // Manejar si el logo no se encuentra, la aplicación seguirá funcionando
        }

        // Título de la factura
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Factura de Compra", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);

        // Tabla de datos de la factura
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);
        
        addInfoCell(infoTable, "Orden de Compra:", reportData.getPurchaseOrder());
        addInfoCell(infoTable, "Fecha de Compra:", reportData.getPurchaseDate().toString());
        addInfoCell(infoTable, "No. de Factura:", reportData.getInvoiceNumber());
        addInfoCell(infoTable, "No. de Autorizacion:", reportData.getAuthorizationCode());
        addInfoCell(infoTable, "Proveedor:", reportData.getSupplierName());
        addInfoCell(infoTable, "RUC:", reportData.getSupplierRuc());

        document.add(infoTable);
        document.add(Chunk.NEWLINE);

        // Tabla de detalles
        PdfPTable detailsTable = new PdfPTable(5);
        detailsTable.setWidthPercentage(100);
        detailsTable.setWidths(new float[]{1.5f, 4.5f, 2f, 2f, 2f});
        
        Stream.of("CÓDIGO", "PRODUCTO", "CANTIDAD", "PRECIO UNIT.", "TOTAL")
            .forEach(headerTitle -> {
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(headerTitle));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                detailsTable.addCell(header);
            });
        
        for (PurchaseDetailReportDTO detail : reportData.getDetails()) {
            detailsTable.addCell(detail.getProductCode());
            detailsTable.addCell(detail.getProductName());
            detailsTable.addCell(String.valueOf(detail.getQuantity()));
            detailsTable.addCell(String.format("%.2f", detail.getUnitPrice()));
            detailsTable.addCell(String.format("%.2f", detail.getTotal()));
        }
        document.add(detailsTable);
        document.add(Chunk.NEWLINE);

        // Tabla de totales
        PdfPTable totalsTable = new PdfPTable(2);
        totalsTable.setWidthPercentage(40);
        totalsTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
        addTotalCell(totalsTable, "Subtotal:", String.format("%.2f", reportData.getSubtotal()));
        addTotalCell(totalsTable, "SubtotalIva:", String.format("%.2f", reportData.getSubtotalIva()));
        addTotalCell(totalsTable, "SubtotalIva0:", String.format("%.2f", reportData.getSubtotalIva0()));
        addTotalCell(totalsTable, "IRBP:", String.format("%.2f", reportData.getIrbp()));
        addTotalCell(totalsTable, "ICE:", String.format("%.2f", reportData.getIce()));
        addTotalCell(totalsTable, "Descuento:", String.format("%.2f", reportData.getDiscount()));
        addTotalCell(totalsTable, "IVA:", String.format("%.2f", reportData.getIva()));
        addTotalCell(totalsTable, "TOTAL:", String.format("%.2f", reportData.getTotalAmount()));
        
        document.add(totalsTable);

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addInfoCell(PdfPTable table, String label, String value) {
        table.addCell(createCell(label, true));
        table.addCell(createCell(value, false));
    }
    
    private void addTotalCell(PdfPTable table, String label, String value) {
        PdfPCell labelCell = new PdfPCell(new Phrase(label));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(labelCell);

        PdfPCell valueCell = new PdfPCell(new Phrase(value));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(valueCell);
    }
    
    private PdfPCell createCell(String text, boolean bold) {
        Font font = bold ? FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10) : FontFactory.getFont(FontFactory.HELVETICA, 10);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}