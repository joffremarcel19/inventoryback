package com.planificacion.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.OneToMany;

@Entity
@Table(name = "menu")
public class Menu {

	@Id
	private int idMenu;
	@Column(name = "nombre", length = 150)
	private String nombre;
	@Column(name = "url", length = 100)
	private String url;
	@Column(name = "icono", length = 50)
	private String icono;
	@JsonManagedReference
	@OneToMany(mappedBy = "parentMenu", fetch = FetchType.LAZY)
    private List<Submenu> submenus;
  
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "menu_rol", joinColumns = @JoinColumn(name = "id_menu", referencedColumnName = "idMenu"), inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "idRol"))
	private List<Rol> roles;

	public int getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}

	public List<Submenu> getSubmenus() {
		return submenus;
	}

	public void setSubmenus(List<Submenu> submenus) {
		this.submenus = submenus;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
