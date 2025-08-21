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
@Table(name="pei")
public class Pei {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPei;
		
	@ManyToOne
	@JoinColumn(name = "idUniAdm", nullable = false, foreignKey = @ForeignKey(name = "FK_pei_unidadAdministrativa"))
	private UnidadAdministrativa unidadAdministrativa;
		
	@Column(name="anioReportado")
	private int anioReportado;
	
	@Column(name="fechaReportado")
	private LocalDateTime fechaReportado;
	
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

	public int getIdPei() {
		return idPei;
	}

	public void setIdPei(int idPei) {
		this.idPei = idPei;
	}

	public UnidadAdministrativa getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	public void setUnidadAdministrativa(UnidadAdministrativa unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	
}
