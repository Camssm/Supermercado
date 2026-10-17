package com.app.Producto.entity;

import java.math.BigDecimal;

import com.app.Producto.enums.Categoria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String nombre;
	private String codigoDeBarras;
	private Categoria categoria;
	private String descripcion;
	private String marca;
	private BigDecimal precioUnitario;
	private String imagenUrl;
	private int stock;

	
}