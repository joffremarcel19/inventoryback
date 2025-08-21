package com.planificacion.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="unidadadministrativa")
public class UnidadAdministrativa {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUniAdm;
	
	@Column(name="nombreuni")
	private String nombreUni;
	
	@Column(name="nivel")
	private String nivel;
	
	@ManyToOne
	@JoinColumn(name = "idUnidadP", nullable = false, foreignKey = @ForeignKey(name = "FK_UnidadP_uniAdm"))
	private UnidadPrincipal unidadPrincipal;
		
}
