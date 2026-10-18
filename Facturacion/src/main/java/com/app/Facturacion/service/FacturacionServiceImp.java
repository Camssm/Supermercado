package com.app.Facturacion.service;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.app.Facturacion.config.RabbitMQConfig;
import com.app.Facturacion.senders.FacturacionEvent;
import com.app.Facturacion.dto.FacturacionAgregarDto;
import com.app.Facturacion.dto.FacturacionDto;
import com.app.Facturacion.entity.Facturacion;
import com.app.Facturacion.repository.FacturacionRepository;
import com.app.Facturacion.dto.ProductoDto;

@Service
public class FacturacionServiceImp implements FacturacionService {

	@Autowired
	private FacturacionRepository facturacionRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Override
	public List<FacturacionDto> listar() {
		return facturacionRepository.findAll().stream().map(this::convertirADto).toList();
	}

	@Override
	public FacturacionDto agregar(FacturacionAgregarDto dto) {
		if (dto == null || dto.getDetalles() == null || dto.getDetalles().isEmpty()) {
			throw new IllegalArgumentException("La factura debe tener al menos un producto");
		}

		Facturacion facturacion = dto.mapperTo();

		Map<Long, Integer> cantidades = new HashMap<>();

		for (var detalle : facturacion.getDetalles()) {
			if (detalle.getProductoId() == null || detalle.getProductoId() <= 0) {
				throw new IllegalArgumentException("El ID del producto no es válido");
			}

			if (detalle.getCantidad() == null || detalle.getCantidad() <= 0) {
				throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
			}

			cantidades.merge(detalle.getProductoId(), detalle.getCantidad(), Integer::sum);
		}

		Map<Long, ProductoDto> productos = new HashMap<>();

		for (Long productoId : cantidades.keySet()) {
			ProductoDto producto = restTemplate.getForObject("http://MS-PRODUCTOS/api/productos/" + productoId,
					ProductoDto.class);

			if (producto == null) {
				throw new RuntimeException("No se pudo obtener el producto " + productoId);
			}

			if (producto.getStock() == null || producto.getStock() < cantidades.get(productoId)) {
				throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
			}

			productos.put(productoId, producto);
		}

		for (var detalle : facturacion.getDetalles()) {
			ProductoDto producto = productos.get(detalle.getProductoId());

			detalle.setNombreProducto(producto.getNombre());
			detalle.setPrecioUnitario(producto.getPrecioUnitario());
			detalle.calcularSubtotal();
		}

		facturacion.setFecha(java.time.LocalDateTime.now());
		facturacion.calcularTotal();

		Facturacion guardada = facturacionRepository.save(facturacion);

		FacturacionEvent evento = new FacturacionEvent();

		evento.setFacturaId(guardada.getId());

		List<FacturacionEvent.ProductoVendidoEvent> productosVendidos = new ArrayList<>();

		for (var detalle : guardada.getDetalles()) {

			FacturacionEvent.ProductoVendidoEvent producto = new FacturacionEvent.ProductoVendidoEvent();

			producto.setProductoId(detalle.getProductoId());
			producto.setCantidad(detalle.getCantidad());

			productosVendidos.add(producto);
		}

		evento.setProductos(productosVendidos);

		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, evento);

		return convertirADto(guardada);

	}

	@Override
	public FacturacionDto buscarPorId(Long id) {
		Facturacion facturacion = facturacionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("No existe la factura con id " + id));

		return convertirADto(facturacion);
	}

	private FacturacionDto convertirADto(Facturacion facturacion) {
		FacturacionDto dto = new FacturacionDto();

		dto.setId(facturacion.getId());
		dto.setFecha(facturacion.getFecha());
		dto.setCliente(facturacion.getCliente());
		dto.setTotal(facturacion.getTotal());

		dto.setDetalles(facturacion.getDetalles().stream().map(detalle -> {
			com.app.Facturacion.dto.DetalleFacturaDto detalleDto = new com.app.Facturacion.dto.DetalleFacturaDto();

			detalleDto.setId(detalle.getId());
			detalleDto.setProductoId(detalle.getProductoId());
			detalleDto.setNombreProducto(detalle.getNombreProducto());
			detalleDto.setPrecioUnitario(detalle.getPrecioUnitario());
			detalleDto.setCantidad(detalle.getCantidad());
			detalleDto.setSubtotal(detalle.getSubtotal());

			return detalleDto;
		}).toList());

		return dto;
	}
}
