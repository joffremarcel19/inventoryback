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
@Table(name="poa")
public class Poa {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPoa;
		
	@ManyToOne
	@JoinColumn(name = "idUniAdm", nullable = false, foreignKey = @ForeignKey(name = "FK_poa_unidadAdministrativa"))
	private UnidadAdministrativa unidadAdministrativa;
		
	@Column(name="anioReportado")
	private int anioReportado;
	
	@Column(name="fechaReportado")
	private LocalDateTime fechaReportado;
	
	@ManyToOne
	@JoinColumn(name = "idObjEstInt", nullable = true, foreignKey = @ForeignKey(name = "FK_poa_objEstInstitucional"))
	private ObjEstInstitucional objEstInstitucional;	
	
	public ObjEstInstitucional getObjEstInstitucional() {
		return objEstInstitucional;
	}

	public void setObjEstInstitucional(ObjEstInstitucional objEstInstitucional) {
		this.objEstInstitucional = objEstInstitucional;
	}

	public int getIdPoa() {
		return idPoa;
	}

	public void setIdPoa(int idPoa) {
		this.idPoa = idPoa;
	}

	public LocalDateTime getFechaReportado() {
		return fechaReportado;
	}

	public void setFechaReportado(LocalDateTime fechaReportado) {
		this.fechaReportado = fechaReportado;
	}

	public int getAnioReportado() {
		return anioReportado;
	}

	public void setAnioReportado(int anioReportado) {
		this.anioReportado = anioReportado;
	}

	public UnidadAdministrativa getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	
}
