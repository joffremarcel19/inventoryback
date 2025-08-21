package com.planificacion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="comercial")
public class Comercial {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idComercio;
    @Column(name="nombre")
	private String nombre;
    @Column(name="descripcion")
	private String descripion;
	@OneToOne(targetEntity = Categoria.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "id_categoria")
	private Categoria categoria;
	public int getIdComercio() {
		return idComercio;
	}
	public void setIdComercio(int idComercio) {
		this.idComercio = idComercio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripion() {
		return descripion;
	}
	public void setDescripion(String descripion) {
		this.descripion = descripion;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
}
