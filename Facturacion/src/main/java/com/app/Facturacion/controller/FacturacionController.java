package com.app.Facturacion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.Facturacion.entity.Facturacion;
import com.app.Facturacion.service.AdministracionService;


@RestController
@RequestMapping("/api/facturacion")
public class FacturacionController { @Autowired
	//DE ACA A ABAJO A CAMBIAR TODOOO
	
	private AdministracionService administracionService;

	@RequestMapping(value = "/Administracion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Facturacion>> listar() 
	{
		return new ResponseEntity<>(administracionService.listar(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/agregar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Facturacion>> agregar(@RequestBody Facturacion facturacion)
	{
		administracionService.agregar(facturacion);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
