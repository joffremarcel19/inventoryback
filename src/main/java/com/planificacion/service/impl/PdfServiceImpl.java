package com.planificacion.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.planificacion.dto.DeliveryReportDTO;
import com.planificacion.dto.PurchaseReportDTO;
import com.planificacion.dto.PurchaseReportDTO.PurchaseDetailReportDTO;
import com.planificacion.model.UnidadAdministrativa;
import com.planificacion.service.IPdfService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
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
    
    private void addDeliveryReportContent(Document document, DeliveryReportDTO reportData) throws Exception {

        // --- LOGO AND HEADER ---
        try {
            Image logo = Image.getInstance(getClass().getResource("/static/imagenes/logo.png"));
            logo.scaleAbsolute(50f, 25f);
            logo.setAlignment(Image.ALIGN_LEFT);
            document.add(logo);
        } catch (Exception e) {
            // Manejar si el logo no se encuentra, la aplicación seguirá funcionando
        }

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 8);
        Paragraph headerTitle = new Paragraph("GOBIERNO AUTONOMO DESCENTRALIZADO\nMUNICIPAL DE MACHALA", titleFont);
        headerTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(headerTitle);

        Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 7);
        Paragraph subHeaderTitle = new Paragraph("SECCION DE MANTENIMIENTO DE EQUIPOS INFORMATICOS", subTitleFont);
        subHeaderTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(subHeaderTitle);
        document.add(Chunk.NEWLINE);

     // --- DELIVERY INFORMATION TABLE ---
        PdfPTable infoTable = new PdfPTable(2);
        infoTable.setWidthPercentage(100);

        // ✅ Usa una fuente más pequeña para esta tabla.
        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 8); // Puedes probar con 7 o 9 si es necesario

        // ✅ Agrega la celda de etiqueta con la fuente más pequeña
        PdfPCell cellLabel = new PdfPCell(new Phrase("EGRESO DE SUMINISTROS N:", infoFont));
        cellLabel.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(cellLabel);
     // Obtén el año actual
        int currentYear = java.time.Year.now().getValue();
        String formattedId = String.format("%03d", reportData.getId());
        String deliveryIdWithYear = formattedId + "-" + String.valueOf(currentYear);
        PdfPCell cellValue = new PdfPCell(new Phrase(deliveryIdWithYear, infoFont));
        cellValue.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(cellValue);
     
        // Repite este patrón para el resto de las filas
        cellLabel = new PdfPCell(new Phrase("FECHA:", infoFont));
        cellLabel.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(cellLabel);
        LocalDate deliveryDate = reportData.getDeliveryDate().toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = deliveryDate.format(formatter);

        cellValue = new PdfPCell(new Phrase(formattedDate, infoFont));
        cellValue.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(cellValue);
        
        //departamento
        cellLabel = new PdfPCell(new Phrase("DEPARTAMENTO:", infoFont));
        cellLabel.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(cellLabel);
        cellValue = new PdfPCell(new Phrase(reportData.getAdministrativeUnitName(), infoFont));
        cellValue.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(cellValue);
        
        //elbaorado por
        cellLabel = new PdfPCell(new Phrase("ELABORADO POR:", infoFont));
        cellLabel.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(cellLabel);

        cellValue = new PdfPCell(new Phrase(reportData.getDeliveredByUsername(), infoFont));
        cellValue.setBorder(Rectangle.NO_BORDER);
        infoTable.addCell(cellValue);

        document.add(infoTable);
        document.add(Chunk.NEWLINE);

        // --- PRODUCT DETAILS TABLE ---
        PdfPTable detailsTable = new PdfPTable(6);
        detailsTable.setWidthPercentage(100);
        detailsTable.setWidths(new float[]{3.5f, 1.5f, 1.5f, 1.5f,1.5f, 1f});

        Stream.of("PRODUCTO", "MARCA", "MODELO", "COLOR", "N° PARTE", "CANTIDAD")
            .forEach(columnTitle -> { 
                PdfPCell header = new PdfPCell();
                header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                header.setBorderWidth(1);
                header.setPhrase(new Phrase(columnTitle, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7)));
                header.setHorizontalAlignment(Element.ALIGN_CENTER);
                header.setVerticalAlignment(Element.ALIGN_MIDDLE);
                detailsTable.addCell(header);
            });

        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 7); 
        for (DeliveryReportDTO.DeliveryDetailReportDTO detail : reportData.getDetails()) {
            detailsTable.addCell(new Phrase(detail.getProductName(), cellFont));
            detailsTable.addCell(new Phrase(detail.getProductBrand(), cellFont));
            detailsTable.addCell(new Phrase(detail.getProductModel(), cellFont));
            detailsTable.addCell(new Phrase(detail.getProductColor(), cellFont));
            detailsTable.addCell(new Phrase(detail.getProductPartNumber(), cellFont));
            detailsTable.addCell(new Phrase(String.valueOf(detail.getQuantity()), cellFont));
        }
        document.add(detailsTable);
        document.add(Chunk.NEWLINE);

        // --- SECCIÓN DE FIRMAS ---
        PdfPTable signaturesTable = new PdfPTable(2);
        signaturesTable.setWidthPercentage(80);
        signaturesTable.setHorizontalAlignment(Element.ALIGN_CENTER);
        signaturesTable.addCell(createFixedSignatureCell("Entregado:", "LCDO. CARLOS RODRIGUEZ COELLO", "DIRECTOR DE TECNOLOGIAS DE LA INFORMACION"));
        signaturesTable.addCell(createDynamicSignatureCell("Recibido:", reportData.getReceivedByPersonFullName(), reportData.getAdministrativeUnitName()));
        document.add(signaturesTable);
    }

    @Override
    public ByteArrayInputStream generateDeliveryReport(DeliveryReportDTO reportData) throws Exception {

    	Document document = new Document(PageSize.A4, 10, 10, 10, 10);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, out);
        document.open();

        for (int i = 0; i < 3; i++) {
            addDeliveryReportContent(document, reportData);
            if (i < 2) {
                document.add(Chunk.NEWLINE);
            }
        }

        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

	private PdfPCell createFixedSignatureCell(String label, String name, String position) {

		PdfPCell cell = new PdfPCell();

		cell.setBorder(Rectangle.NO_BORDER);

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		// Línea de firma

		Paragraph line = new Paragraph("__________________________");

		line.setAlignment(Element.ALIGN_CENTER);

		cell.addElement(line);

		// ✅ Agrega la etiqueta (ej. "Entregado por:")

		Paragraph labelText = new Paragraph(label, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7));

		labelText.setAlignment(Element.ALIGN_CENTER);

		cell.addElement(labelText);

		// Nombre

		Paragraph nameText = new Paragraph(name, FontFactory.getFont(FontFactory.HELVETICA, 7));
		

		nameText.setAlignment(Element.ALIGN_CENTER);

		cell.addElement(nameText);

		// Posición o cargo

		Paragraph positionText = new Paragraph(position, FontFactory.getFont(FontFactory.HELVETICA, 7));

		positionText.setAlignment(Element.ALIGN_CENTER);

		cell.addElement(positionText);

		return cell;

	}

	private PdfPCell createDynamicSignatureCell(String label, String name, String administrativeUnit) {

		PdfPCell cell = new PdfPCell();

		cell.setBorder(Rectangle.NO_BORDER);

		cell.setHorizontalAlignment(Element.ALIGN_CENTER);

		// Línea de firma

		Paragraph line = new Paragraph("__________________________");

		line.setAlignment(Element.ALIGN_CENTER);

		cell.addElement(line);

		// ✅ Agrega la etiqueta (ej. "Recibido por:")

		Paragraph labelText = new Paragraph(label, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 7));

		labelText.setAlignment(Element.ALIGN_CENTER);

		cell.addElement(labelText);

		// Nombre

		Paragraph nameText = new Paragraph(name, FontFactory.getFont(FontFactory.HELVETICA, 7));

		nameText.setAlignment(Element.ALIGN_CENTER);

		cell.addElement(nameText);
		
		
		 //admin
		  Paragraph unitText = new Paragraph(administrativeUnit, FontFactory.getFont(FontFactory.HELVETICA, 7));
		    unitText.setAlignment(Element.ALIGN_CENTER);
		    cell.addElement(unitText);

		return cell;
	
	}
	
}