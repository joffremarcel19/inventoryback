package com.planificacion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="metainstitucional")
public class MetaInstitucional {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idMetIns;
	
	@Column(name="nombremetins")
	private String nombreMetIns;

	public int getIdMetIns() {
		return idMetIns;
	}

	public void setIdMetIns(int idMetIns) {
		this.idMetIns = idMetIns;
	}

	public String getNombreMetIns() {
		return nombreMetIns;
	}

	public void setNombreMetIns(String nombreMetIns) {
		this.nombreMetIns = nombreMetIns;
	}	
	
}
