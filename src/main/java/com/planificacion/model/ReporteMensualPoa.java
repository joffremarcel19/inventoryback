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
@Table(name = "reportemensualPoa")
public class ReporteMensualPoa {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idReportePoa;
	@Column(name = "descripcion")
	private String descripcion;
	@Column(name = "reporte")
	private int reporte;
	@Column(name = "fecha")
	private LocalDateTime fecha;
	@ManyToOne
	@JoinColumn(name = "idActividad", nullable = false, foreignKey = @ForeignKey(name = "FK_actividad_reporte"))
	private ActividadesPoa actividadesPoa;
	
	@Column(name = "medioVerificacion")
	private String medioVerificacion;

	public int getIdReportePoa() {
		return idReportePoa;
	}

	public void setIdReportePoa(int idReportePoa) {
		this.idReportePoa = idReportePoa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ActividadesPoa getActividadesPoa() {
		return actividadesPoa;
	}

	public void setActividadesPoa(ActividadesPoa actividadesPoa) {
		this.actividadesPoa = actividadesPoa;
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

}
