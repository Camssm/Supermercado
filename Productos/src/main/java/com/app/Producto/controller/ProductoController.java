package com.app.Producto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.Producto.entity.Producto;
import com.app.Producto.service.ProductoService;

@RestController
@RequestMapping(value = "/api/alumnos")
public class ProductoController {

	@Autowired
	private ProductoService productoService;
	
	@RequestMapping(value = "/alumnos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Producto>> listar() 
	{
		return new ResponseEntity(productoService.listar(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/agregar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Producto>> agregar(@RequestBody Producto producto)
	{
		productoService.agregar(producto);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}