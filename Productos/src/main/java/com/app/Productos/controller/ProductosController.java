package com.app.Productos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.Productos.entity.Productos;
import com.app.Productos.service.ProductosService;

@RestController
@RequestMapping(value = "/api/alumnos")
public class ProductosController {

	@Autowired
	private ProductosService productosService;
	
	@RequestMapping(value = "/alumnos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Productos>> listar() 
	{
		return new ResponseEntity(productosService.listar(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/agregar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Productos>> agregar(@RequestBody Productos productos)
	{
		productosService.agregar(productos);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}