package com.planificacion.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="detallepoa")
public class DetallePoa {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idDetallePoa;	
	
	@ManyToOne
	@JoinColumn(name = "idPoa", nullable = false, foreignKey = @ForeignKey(name = "FK_poa_detalle"))
	private Poa poa;
	
	@Column(name="nombreproducto")
	private String nombreProducto;

	public int getIdDetallePoa() {
		return idDetallePoa;
	}

	public void setIdDetallePoa(int idDetallePoa) {
		this.idDetallePoa = idDetallePoa;
	}

	public Poa getPoa() {
		return poa;
	}

	public void setPoa(Poa poa) {
		this.poa = poa;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}	
	
}
