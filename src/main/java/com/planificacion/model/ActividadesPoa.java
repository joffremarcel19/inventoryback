package com.planificacion.model;

import java.util.List;

import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="actividadesPoa")
public class ActividadesPoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idActividad;
	@Column(name="actividad")
	private String actividad;
	@Column(name="indicador")
	private String indicador;
	@Column(name="metaProgramada")
	private int metaProgramada;
	@Column(name="trimestreuno")
	private int trimestreuno;
	@Column(name="trimestredos")
	private int trimestredos;
	@Column(name="trimestretres")
	private int trimestretres;
	@Column(name="trimestrecuatro")
	private int trimestrecuatro;
	@Column(name="medioVerificacion")
	private String mediosVerificacion;
	
	@ManyToOne
	@JoinColumn(name = "idDetallePoa", nullable = false, foreignKey = @ForeignKey(name = "FK_actividad_detalle"))
	private DetallePoa detallePoa;	
		
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "actividad_usuario", joinColumns = @JoinColumn(name = "id_actividad", referencedColumnName = "idActividad"), inverseJoinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "idUsuario"))
	private List<Usuario> responsables;		
	
	public DetallePoa getDetallePoa() {
		return detallePoa;
	}

	public void setDetallePoa(DetallePoa detallePoa) {
		this.detallePoa = detallePoa;
	}
	
	public int getIdActividad() {
		return idActividad;
	}

	public void setIdActividad(int idActividad) {
		this.idActividad = idActividad;
	}

	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}

	public int getMetaProgramada() {
		return metaProgramada;
	}

	public void setMetaProgramada(int metaProgramada) {
		this.metaProgramada = metaProgramada;
	}

	public int getTrimestreuno() {
		return trimestreuno;
	}

	public void setTrimestreuno(int trimestreuno) {
		this.trimestreuno = trimestreuno;
	}

	public int getTrimestredos() {
		return trimestredos;
	}

	public void setTrimestredos(int trimestredos) {
		this.trimestredos = trimestredos;
	}

	public int getTrimestretres() {
		return trimestretres;
	}

	public void setTrimestretres(int trimestretres) {
		this.trimestretres = trimestretres;
	}

	public int getTrimestrecuatro() {
		return trimestrecuatro;
	}

	public void setTrimestrecuatro(int trimestrecuatro) {
		this.trimestrecuatro = trimestrecuatro;
	}

	public String getMediosVerificacion() {
		return mediosVerificacion;
	}

	public void setMediosVerificacion(String mediosVerificacion) {
		this.mediosVerificacion = mediosVerificacion;
	}

	public List<Usuario> getResponsables() {
		return responsables;
	}

	public void setResponsables(List<Usuario> responsables) {
		this.responsables = responsables;
	}	
}
