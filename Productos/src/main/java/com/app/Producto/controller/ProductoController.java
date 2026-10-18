package com.app.Producto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.Producto.dto.ProductoAgregarDto;
import com.app.Producto.dto.ProductoDto;
import com.app.Producto.dto.ProductoEditarDto;
import com.app.Producto.service.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

	@Autowired
	private ProductoService productoService;

	@RequestMapping(value = "/productos", method = RequestMethod.GET)
	public ResponseEntity<List<ProductoDto>> listar() throws Exception {
		return ResponseEntity.ok(productoService.listar());
	}

	@RequestMapping(value = "/agregar", method = RequestMethod.PUT)
	public ResponseEntity<Void> agregar(@RequestHeader(value = "X-User-Rol", required = false) String rol,
			@Valid @RequestBody ProductoAgregarDto productoDto) throws Exception {
		
		if (!esAdmin(rol)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		productoService.agregar(productoDto);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProductoDto> obtenerPorId(@PathVariable Long id) throws Exception {

		return ResponseEntity.ok(productoService.obtenerPorId(id));
	}

	@RequestMapping(value = "/editar", method = RequestMethod.PUT)
	public ResponseEntity<Void> editar(@RequestHeader(value = "X-User-Rol", required = false) String rol,
			@Valid @RequestBody ProductoEditarDto productoDto) throws Exception {

		if (!esAdmin(rol)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		productoService.editar(productoDto);

		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> eliminar(@PathVariable Long id,
			@RequestHeader(value = "X-User-Rol", required = false) String rol) throws Exception {

		if (!esAdmin(rol)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		productoService.eliminar(id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}/stock/descontar", method = RequestMethod.PUT)
	public ResponseEntity<Void> descontarStock(@PathVariable Long id, @RequestParam Integer cantidad) {

		productoService.descontarStock(id, cantidad);

		return ResponseEntity.ok().build();
	}

	@RequestMapping(value = "/{id}/stock/ingresar", method = RequestMethod.PUT)
	public ResponseEntity<Void> ingresarStock(@PathVariable Long id, @RequestParam Integer cantidad,
			@RequestHeader(value = "X-User-Rol", required = false) String rol) {

		if (!esAdmin(rol)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}

		productoService.ingresarStock(id, cantidad);

		return ResponseEntity.ok().build();
	}

	private boolean esAdmin(String rol) {
		return "ADMIN".equalsIgnoreCase(rol);
	}
}
