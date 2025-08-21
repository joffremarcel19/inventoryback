package com.planificacion.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="parametro")
public class Parametro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idParametro;
	@Column(name="nombreParametro")
	private String nombreParametro;
	
	@Column(name="urlImagen")
	private String urlImagen;
	
	public int getIdParametro() {
		return idParametro;
	}
	public void setIdParametro(int idParametro) {
		this.idParametro = idParametro;
	}
	public String getNombreParametro() {
		return nombreParametro;
	}
	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}
	public String getUrlImagen() {
		return urlImagen;
	}
	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}
	
	
}
