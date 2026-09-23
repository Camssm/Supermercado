package com.app.Stock.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="cursos")
public class Stock {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String materia;
	private String nombreMaestro;
	private int numSalon;
	
	@Column(name = "administracion_id")
	private long administracionId;
	
	public Stock() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMateria() {
		return materia;
	}

	public void setMateria(String materia) {
		this.materia = materia;
	}

	public String getNombreMaestro() {
		return nombreMaestro;
	}

	public void setNombreMaestro(String nombreMaestro) {
		this.nombreMaestro = nombreMaestro;
	}

	public int getNumSalon() {
		return numSalon;
	}

	public void setNumSalon(int numSalon) {
		this.numSalon = numSalon;
	}
	public long getAdministracionId() {
		return administracionId;
	}

	public void setAdministracionId(long administracionId) {
		this.administracionId = administracionId;
	}

	
}