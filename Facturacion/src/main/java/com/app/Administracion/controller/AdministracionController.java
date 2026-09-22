package com.app.Administracion.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.Administracion.entity.Administracion;
import com.app.Administracion.service.AdministracionService;


@RestController
@RequestMapping("/api/administracion")
public class AdministracionController { @Autowired
	private AdministracionService administracionService;

	@RequestMapping(value = "/Administracion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Administracion>> listar() 
	{
		return new ResponseEntity<>(administracionService.listar(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/agregar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Administracion>> agregar(@RequestBody Administracion administracion)
	{
		administracionService.agregar(administracion);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}
