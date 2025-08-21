package com.planificacion.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="resettoken")
public class ResetToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer Id;
	@Column(nullable = false, unique = true)
	private String token;
	@OneToOne(targetEntity = Usuario.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "id_usuario")
	private Usuario user;
	@Column(nullable = false)
	private LocalDateTime expiracion;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}

	public LocalDateTime getExpiracion() {
		return expiracion;
	}

	public void setExpiracion(int minutos) {
		LocalDateTime hoy = LocalDateTime.now();
		LocalDateTime exp = hoy.plusMinutes(minutos);
		this.expiracion = exp;
	}

	public boolean estaExpirado() {
		return LocalDateTime.now().isAfter(this.expiracion);
	}
}
