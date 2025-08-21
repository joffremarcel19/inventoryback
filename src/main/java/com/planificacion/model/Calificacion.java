package com.planificacion.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "calificacion")
public class Calificacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idCalificacion;

	@ManyToOne
	@JoinColumn(name = "idUsuario", nullable = false, foreignKey = @ForeignKey(name = "FK_calificacion_usuario"))
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "idComercial", nullable = false, foreignKey = @ForeignKey(name = "FK_calificacion_comercial"))
	private Comercial comercial;
	
	@ManyToOne
	@JoinColumn(name = "idParametro", nullable = false, foreignKey = @ForeignKey(name = "FK_calificacion_parametro"))
	private Parametro parametro;
	
	@Column(name = "fecha_calificacion")
	private LocalDateTime fechaCalificacion;

	@Column(name = "valor")
	private double valor;


	public int getIdCalificacion() {
		return idCalificacion;
	}

	public void setIdCalificacion(int idCalificacion) {
		this.idCalificacion = idCalificacion;
	}

	public LocalDateTime getFechaCalificacion() {
		return fechaCalificacion;
	}

	public void setFechaCalificacion(LocalDateTime fechaCalificacion) {
		this.fechaCalificacion = fechaCalificacion;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Comercial getComercial() {
		return comercial;
	}

	public void setComercial(Comercial comercial) {
		this.comercial = comercial;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

}
