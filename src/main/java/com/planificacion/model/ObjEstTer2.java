package com.planificacion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="objestter2")
public class ObjEstTer2 {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idObjEst2;
	
	@Column(name="nombreobjest2")
	private String nombreObjEst2;

	public int getIdObjEst2() {
		return idObjEst2;
	}

	public void setIdObjEst2(int idObjEst2) {
		this.idObjEst2 = idObjEst2;
	}

	public String getNombreObjEst2() {
		return nombreObjEst2;
	}

	public void setNombreObjEst2(String nombreObjEst2) {
		this.nombreObjEst2 = nombreObjEst2;
	}	
	
}
