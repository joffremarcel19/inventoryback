package com.planificacion.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reportemensual")
public class ReporteMensual {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReporte;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "reporte")
	private int reporte;
	@Column(name = "fecha")
	private LocalDateTime fecha;
	@ManyToOne
	@JoinColumn(name = "idDetallePei", nullable = false, foreignKey = @ForeignKey(name = "FK_detallepei_reporte"))
	private DetallePei detallePei;
	
	@Column(name = "medioVerificacion")
	private String medioVerificacion;

	public int getIdReporte() {
		return idReporte;
	}

	public void setIdReporte(int idReporte) {
		this.idReporte = idReporte;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getReporte() {
		return reporte;
	}

	public void setReporte(int reporte) {
		this.reporte = reporte;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public String getMedioVerificacion() {
		return medioVerificacion;
	}

	public void setMedioVerificacion(String medioVerificacion) {
		this.medioVerificacion = medioVerificacion;
	}

	public DetallePei getDetallePei() {
		return detallePei;
	}

	public void setDetallePei(DetallePei detallePei) {
		this.detallePei = detallePei;
	}

}
