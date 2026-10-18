package com.app.Facturacion.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.app.Facturacion.config.RabbitMQConfig;
import com.app.Facturacion.dto.DetalleFacturaDto;
import com.app.Facturacion.dto.FacturacionAgregarDto;
import com.app.Facturacion.dto.FacturacionDto;
import com.app.Facturacion.dto.ProductoDto;
import com.app.Facturacion.entity.Facturacion;
import com.app.Facturacion.exception.BusinessException;
import com.app.Facturacion.exception.FacturaNoEncontradaException;
import com.app.Facturacion.repository.FacturacionRepository;
import com.app.Facturacion.senders.FacturacionEvent;

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
	@Transactional
	public FacturacionDto agregar(FacturacionAgregarDto dto) {
		// La forma del DTO (detalles, IDs, cantidades) ya la validó @Valid en el controller.
		Facturacion facturacion = dto.mapperTo();

		Map<Long, Integer> cantidades = new HashMap<>();

		for (var detalle : facturacion.getDetalles()) {
			cantidades.merge(detalle.getProductoId(), detalle.getCantidad(), Integer::sum);
		}

		Map<Long, ProductoDto> productos = new HashMap<>();

		for (Long productoId : cantidades.keySet()) {
			ProductoDto producto = obtenerProducto(productoId);

			if (producto.getStock() == null || producto.getStock() < cantidades.get(productoId)) {
				throw new BusinessException("F-409", HttpStatus.CONFLICT,
						"Stock insuficiente para el producto: " + producto.getNombre());
			}

			productos.put(productoId, producto);
		}

		for (var detalle : facturacion.getDetalles()) {
			ProductoDto producto = productos.get(detalle.getProductoId());

			detalle.setNombreProducto(producto.getNombre());
			detalle.setPrecioUnitario(producto.getPrecioUnitario());
			detalle.calcularSubtotal();
		}

		facturacion.setFecha(LocalDateTime.now());
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
				.orElseThrow(() -> new FacturaNoEncontradaException(id));

		return convertirADto(facturacion);
	}

	private ProductoDto obtenerProducto(Long productoId) {
		try {
			ProductoDto producto = restTemplate.getForObject(
					"http://MS-PRODUCTOS/api/productos/" + productoId, ProductoDto.class);

			if (producto == null) {
				throw new BusinessException("F-502", HttpStatus.BAD_GATEWAY,
						"El servicio de productos devolvió una respuesta vacía");
			}
			return producto;

		} catch (HttpClientErrorException.NotFound e) {
			throw new BusinessException("F-404", HttpStatus.NOT_FOUND,
					"El producto " + productoId + " no existe");

		} catch (ResourceAccessException | IllegalStateException e) {
			throw new BusinessException("F-503", HttpStatus.SERVICE_UNAVAILABLE,
					"El servicio de productos no está disponible. Intentá de nuevo en unos minutos");
		}
	}

	private FacturacionDto convertirADto(Facturacion facturacion) {
		FacturacionDto dto = new FacturacionDto();

		dto.setId(facturacion.getId());
		dto.setFecha(facturacion.getFecha());
		dto.setCliente(facturacion.getCliente());
		dto.setTotal(facturacion.getTotal());

		dto.setDetalles(facturacion.getDetalles().stream().map(detalle -> {
			DetalleFacturaDto detalleDto = new DetalleFacturaDto();

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