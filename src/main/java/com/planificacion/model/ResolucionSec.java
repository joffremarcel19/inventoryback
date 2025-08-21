package com.planificacion.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="resolucionSec")
public class ResolucionSec {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int idResolucion;
	@Column(name="nOficio")
	public Integer nOficio;
	@Column(name="nResolucion")
	public Integer nResolucion;
	@Column(name="nMemorando")
	public Integer nMemorando;
	@Column(name="fechaSesion")
	public LocalDateTime fechaSesion;
	@Column(name="motivoProceso")
	public String motivoProceso;
	@Column(name="detalle")
	public String detalle;
	@OneToOne
    @JoinColumn(name = "responsable", updatable = false, nullable = false)
	public Usuario usuario;
	@Column(name="observacion")
	public String observacion;
	@Column(name="linkResolucionPDF")
	public String linkResolucionPDF;
	@Column(name="linkResolucionEditable")
	public String linkResolucionEditable;
	@Column(name="linkExpediente")
	public String linkExpediente;
	@Column(name="linkNotificacion")
	public String linkNotificacion;
	@Column(name="anioReportado")
	private int anioReportado;
	
	public int getAnioReportado() {
		return anioReportado;
	}
	public void setAnioReportado(int anioReportado) {
		this.anioReportado = anioReportado;
	}
	public int getIdResolucion() {
		return idResolucion;
	}
	public void setIdResolucion(int idResolucion) {
		this.idResolucion = idResolucion;
	}
	public Integer getnOficio() {
		return nOficio;
	}
	public void setnOficio(Integer nOficio) {
		this.nOficio = nOficio;
	}
	public Integer getnResolucion() {
		return nResolucion;
	}
	public void setnResolucion(Integer nResolucion) {
		this.nResolucion = nResolucion;
	}
	public Integer getnMemorando() {
		return nMemorando;
	}
	public void setnMemorando(Integer nMemorando) {
		this.nMemorando = nMemorando;
	}
	public LocalDateTime getFechaSesion() {
		return fechaSesion;
	}
	public void setFechaSesion(LocalDateTime fechaSesion) {
		this.fechaSesion = fechaSesion;
	}
	public String getMotivoProceso() {
		return motivoProceso;
	}
	public void setMotivoProceso(String motivoProceso) {
		this.motivoProceso = motivoProceso;
	}
	public String getDetalle() {
		return detalle;
	}
	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getLinkResolucionPDF() {
		return linkResolucionPDF;
	}
	public void setLinkResolucionPDF(String linkResolucionPDF) {
		this.linkResolucionPDF = linkResolucionPDF;
	}
	public String getLinkResolucionEditable() {
		return linkResolucionEditable;
	}
	public void setLinkResolucionEditable(String linkResolucionEditable) {
		this.linkResolucionEditable = linkResolucionEditable;
	}
	public String getLinkExpediente() {
		return linkExpediente;
	}
	public void setLinkExpediente(String linkExpediente) {
		this.linkExpediente = linkExpediente;
	}
	public String getLinkNotificacion() {
		return linkNotificacion;
	}
	public void setLinkNotificacion(String linkNotificacion) {
		this.linkNotificacion = linkNotificacion;
	}	
	
}
