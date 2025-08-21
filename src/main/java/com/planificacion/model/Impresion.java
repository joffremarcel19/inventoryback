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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "impresion")
public class Impresion {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idImpresion;

	@Column(name = "textoImpresion", length = 2500)
	private String textoImpresion;

	@Column(name = "fechaImpresion")
	private LocalDateTime fechaImpresion;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "impresion_persona", joinColumns = @JoinColumn(name = "id_impresion", referencedColumnName = "idImpresion"), inverseJoinColumns = @JoinColumn(name = "id_persona", referencedColumnName = "idPersona"))
	private List<Persona> personas;

	@OneToOne
    @JoinColumn(name = "personaenvia", updatable = false, nullable = false)
    private Persona persona;

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public int getIdImpresion() {
		return idImpresion;
	}

	public void setIdImpresion(int idImpresion) {
		this.idImpresion = idImpresion;
	}

	public String getTextoImpresion() {
		return textoImpresion;
	}

	public void setTextoImpresion(String textoImpresion) {
		this.textoImpresion = textoImpresion;
	}

	public LocalDateTime getFechaImpresion() {
		return fechaImpresion;
	}

	public void setFechaImpresion(LocalDateTime fechaImpresion) {
		this.fechaImpresion = fechaImpresion;
	}

	public List<Persona> getPersonas() {
		return personas;
	}

	public void setPersonas(List<Persona> personas) {
		this.personas = personas;
	}

}
