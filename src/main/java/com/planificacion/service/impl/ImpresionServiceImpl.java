package com.planificacion.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.planificacion.model.Impresion;
import com.planificacion.model.PersonaImpresion;
import com.planificacion.repo.IGenericRepo;
import com.planificacion.repo.IImpresionRepo;
import com.planificacion.service.IImpresionService;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class ImpresionServiceImpl extends CRUDImpl<Impresion, Integer> implements IImpresionService {

	@Autowired
	private IImpresionRepo repo;
		
	@Override
	protected IGenericRepo<Impresion, Integer> getRepo() {
		return repo;
	}

	/*
	 * @Override public byte[] generarReporte() { byte[] data = null; Map<String,
	 * Object> parametros = new HashMap<String, Object>();
	 * 
	 * parametros.put("fechaipresion", "Fecha de Impresion");
	 * 
	 * try { File file = new
	 * ClassPathResource("/reports/impresion.jasper").getFile(); JasperPrint print =
	 * JasperFillManager.fillReport(file.getPath(), parametros, new
	 * JRBeanCollectionDataSource(this.listarResumen())); data =
	 * JasperExportManager.exportReportToPdf(print); } catch (Exception e) {
	 * e.printStackTrace(); } return data; }
	 */

	/*
	 * @Override public byte[] generarReporte() { byte[] data = null; Map<String,
	 * Object> parametros = new HashMap<String, Object>();
	 * 
	 * parametros.put("fechaimpresion", "Fecha de Impresion");
	 * 
	 * try { File file = new
	 * ClassPathResource("/reports/impresion.jasper").getFile();
	 * 
	 * JasperPrint print = JasperFillManager.fillReport(file.getPath(), parametros);
	 * 
	 * //InputStream template =
	 * JasperRunnable.class.getResourceAsStream("/reports/impresion.jasper");
	 * //JasperReport report = JasperCompileManager.compileReport(template);
	 * //Map<String, Object> sourceData = new HashMap<>(); //JasperPrint print =
	 * JasperFillManager.fillReport(report, sourceData); File pdf =
	 * File.createTempFile("output.", ".pdf"); System.out.println("Lo exportamos a "
	 * + pdf.getAbsolutePath()); JasperExportManager.exportReportToPdfStream(print,
	 * new FileOutputStream(pdf));
	 * 
	 * 
	 * data = JasperExportManager.exportReportToPdf(print); } catch (Exception e) {
	 * e.printStackTrace(); } return data; }
	 */

	@Override
	public List<PersonaImpresion> listarPersona(int idImpresion) {
		// cantidad fecha
		// List<Object[]> lista.get(0); | [0{cantidad},1{fecha}]
		// [1, "07/11/2020"]
		// [2, "14/11/2020"]
		// [3, "24/10/2020"]

		List<PersonaImpresion> personas = new ArrayList<>();
		
		repo.listarPersona(idImpresion).forEach(x->{
			PersonaImpresion person= new PersonaImpresion();
			person.setNombresCompletos(String.valueOf(x[2])+" "+String.valueOf(x[1]));
			person.setAdjetivo(String.valueOf(x[3]));
			person.setEmpresa(String.valueOf(x[5]));
			person.setPuesto(String.valueOf(x[4]));
			//System.out.print(person);
			personas.add(person);
		});

		return personas;
	}

	@Override
	public byte[] generarReporte(int idImpresion) {		
		byte[] data = null;
		Map<String, Object> parametros = new HashMap<String, Object>();
		
		/// Aqui va el C
		Impresion impresion= new Impresion();
		impresion=repo.findById(idImpresion).get();
		String nombres;
		nombres=impresion.getPersona().getApellidos() +" "+impresion.getPersona().getNombres();
		
		parametros.put("texto",impresion.getTextoImpresion());
		parametros.put("nombreenvia",nombres);
		parametros.put("titulopersona", impresion.getPersona().getPuesto());
		
		try {
			File file = new ClassPathResource("/reports/impresion.jasper").getFile();
		
			JasperPrint print = JasperFillManager.fillReport(file.getPath(), parametros,new JRBeanCollectionDataSource(this.listarPersona(idImpresion)));
			
			File pdf = File.createTempFile("output.", ".pdf");
			System.out.println("Lo exportamos a "
					  + pdf.getAbsolutePath()); 
			JasperExportManager.exportReportToPdfStream(print,
					  new FileOutputStream(pdf));
			data = JasperExportManager.exportReportToPdf(print); // mitocode jasperreports |
			// excel, pdf, ppt, word, csv
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}

}
