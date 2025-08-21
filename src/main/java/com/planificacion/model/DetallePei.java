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
@Table(name="detallepei")
public class DetallePei {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idDetallePei;

	@ManyToOne
	@JoinColumn(name = "idObjEstIns", nullable = false, foreignKey = @ForeignKey(name = "FK_pei_ObjEst"))
	private ObjEstInstitucional objEstInstitucional;	
	@Column(name="objetivotactico")
	private String objetivoTactico;
	@Column(name="objetivooperativo")
	private String objetivoOperativo;
	@Column(name="objetivofuncional")
	private String objetivoFuncional;	
	@Column(name="estrategia")
	private String estrategia;
	@Column(name="metaespecifica")
	private String metaEspecifica;
	@Column(name="valormetaesp")
	private int valorMetaEsp;
	@Column(name="unimetaesp")
	private String uniMetaEsp;
	@Column(name="valormetains")
	private int valorMetaIns;
	@Column(name="unimetains")
	private String uniMetaIns;
	@Column(name="indicador")
	private String indicador;
	@Column(name="metodocalculo")
	private String metodoCalculo;
	@Column(name="metaplanificada")
	private int metaPlanificada;	
	
	@ManyToOne
	@JoinColumn(name = "idMetIns", nullable = false, foreignKey = @ForeignKey(name = "FK_pei_metains"))
	private MetaInstitucional metainstitucional;
	@ManyToOne
	@JoinColumn(name = "idObjEst1", nullable = false, foreignKey = @ForeignKey(name = "FK_pei_objestter1"))
	private ObjEstTer1 objEstTer1;
	@ManyToOne
	@JoinColumn(name = "idObjEst2", nullable = false, foreignKey = @ForeignKey(name = "FK_pei_objestter2"))
	private ObjEstTer2 objEstTer2;
	
	@ManyToOne
	@JoinColumn(name = "idPei", nullable = false, foreignKey = @ForeignKey(name = "FK_pei_reporte"))
	private Pei pei;
		
	public int getIdDetallePei() {
		return idDetallePei;
	}

	public void setIdDetallePei(int idDetallePei) {
		this.idDetallePei = idDetallePei;
	}

	public Pei getPei() {
		return pei;
	}

	public void setPei(Pei pei) {
		this.pei = pei;
	}

	public String getObjetivoFuncional() {
		return objetivoFuncional;
	}

	public void setObjetivoFuncional(String objetivoFuncional) {
		this.objetivoFuncional = objetivoFuncional;
	}

	public ObjEstInstitucional getObjEstInstitucional() {
		return objEstInstitucional;
	}

	public void setObjEstInstitucional(ObjEstInstitucional objEstInstitucional) {
		this.objEstInstitucional = objEstInstitucional;
	}

	public String getObjetivoTactico() {
		return objetivoTactico;
	}

	public void setObjetivoTactico(String objetivoTactico) {
		this.objetivoTactico = objetivoTactico;
	}

	public String getObjetivoOperativo() {
		return objetivoOperativo;
	}

	public void setObjetivoOperativo(String objetivoOperativo) {
		this.objetivoOperativo = objetivoOperativo;
	}

	public String getEstrategia() {
		return estrategia;
	}

	public void setEstrategia(String estrategia) {
		this.estrategia = estrategia;
	}

	public String getMetaEspecifica() {
		return metaEspecifica;
	}

	public void setMetaEspecifica(String metaEspecifica) {
		this.metaEspecifica = metaEspecifica;
	}

	public int getValorMetaEsp() {
		return valorMetaEsp;
	}

	public void setValorMetaEsp(int valorMetaEsp) {
		this.valorMetaEsp = valorMetaEsp;
	}

	public String getUniMetaEsp() {
		return uniMetaEsp;
	}

	public void setUniMetaEsp(String uniMetaEsp) {
		this.uniMetaEsp = uniMetaEsp;
	}

	public int getValorMetaIns() {
		return valorMetaIns;
	}

	public void setValorMetaIns(int valorMetaIns) {
		this.valorMetaIns = valorMetaIns;
	}

	public String getUniMetaIns() {
		return uniMetaIns;
	}

	public void setUniMetaIns(String uniMetaIns) {
		this.uniMetaIns = uniMetaIns;
	}

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}

	public String getMetodoCalculo() {
		return metodoCalculo;
	}

	public void setMetodoCalculo(String metodoCalculo) {
		this.metodoCalculo = metodoCalculo;
	}

	public int getMetaPlanificada() {
		return metaPlanificada;
	}

	public void setMetaPlanificada(int metaPlanificada) {
		this.metaPlanificada = metaPlanificada;
	}

	public MetaInstitucional getMetainstitucional() {
		return metainstitucional;
	}

	public void setMetainstitucional(MetaInstitucional metainstitucional) {
		this.metainstitucional = metainstitucional;
	}

	public ObjEstTer1 getObjEstTer1() {
		return objEstTer1;
	}

	public void setObjEstTer1(ObjEstTer1 objEstTer1) {
		this.objEstTer1 = objEstTer1;
	}

	public ObjEstTer2 getObjEstTer2() {
		return objEstTer2;
	}

	public void setObjEstTer2(ObjEstTer2 objEstTer2) {
		this.objEstTer2 = objEstTer2;
	}
}
