package com.planificacion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="objestinstitucional")
public class ObjEstInstitucional {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idObjEstInt;
	
	@Column(name="nombreobj")
	private String nombreObj;

	public int getIdObjEstInt() {
		return idObjEstInt;
	}

	public void setIdObjEstInt(int idObjEstInt) {
		this.idObjEstInt = idObjEstInt;
	}

	public String getNombreObj() {
		return nombreObj;
	}

	public void setNombreObj(String nombreObj) {
		this.nombreObj = nombreObj;
	}
	
}
