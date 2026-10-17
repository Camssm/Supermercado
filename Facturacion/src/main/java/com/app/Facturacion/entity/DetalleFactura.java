package com.app.Facturacion.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_factura")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleFactura {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer productoId;
	private String nombreProducto;
	private BigDecimal precioUnitario;
	private Integer cantidad;
	private BigDecimal subtotal;

	public void calcularSubtotal() {
		this.subtotal = precioUnitario.multiply(BigDecimal.valueOf(cantidad));
	}
}