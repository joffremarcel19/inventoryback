package com.planificacion.model;

public class Archivo {
	public String url;
	public String name;
	
	public Archivo() {		
	}

	public Archivo(String url, String name) {
		this.url = url;
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
}
