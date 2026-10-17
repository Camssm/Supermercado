package com.app.Facturacion.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "facturacion")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Facturacion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDateTime fecha;
	private String cliente;
	private BigDecimal total;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "factura_id")
	private List<DetalleFactura> detalles = new ArrayList<>();

	public void calcularTotal() {
		this.total = BigDecimal.ZERO;
		for (DetalleFactura detalle : detalles) {
			this.total = this.total.add(detalle.getSubtotal());
		}
	}

}