package com.app.Producto.dto;

import java.math.BigDecimal;

import com.app.Producto.enums.Categoria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDto {

	private Long id;
	private String nombre;
	private String codigoDeBarras;
	private Categoria categoria;
	private String descripcion;
	private String marca;
	private BigDecimal precioUnitario;
	private String imagenUrl;
	private Integer stock;

}