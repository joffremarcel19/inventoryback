package com.planificacion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="objestter1")
public class ObjEstTer1 {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idObjEst1;
	@Column(name="nombreobjest1")
	private String nombreObjEst1;
	public int getIdObjEst1() {
		return idObjEst1;
	}
	public void setIdObjEst1(int idObjEst1) {
		this.idObjEst1 = idObjEst1;
	}
	public String getNombreObjEst1() {
		return nombreObjEst1;
	}
	public void setNombreObjEst1(String nombreObjEst1) {
		this.nombreObjEst1 = nombreObjEst1;
	}	
}
