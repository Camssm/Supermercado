package com.app.Producto.listener.objects;

import java.util.List;

import lombok.Data;

@Data
public class FacturacionEvent {

	private Long facturaId;

	private List<ProductoVendidoEvent> productos;

	@Data
	public static class ProductoVendidoEvent {

		private Long productoId;

		private Integer cantidad;
	}
}
