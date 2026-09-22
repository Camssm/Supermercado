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

import com.app.Productos.entity.Alumno;
import com.app.Productos.service.AlumnoService;

@RestController
@RequestMapping(value = "/api/alumnos")
public class AlumnoController {

	@Autowired
	private AlumnoService alumnoService;
	
	@RequestMapping(value = "/alumnos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Alumno>> listar() 
	{
		return new ResponseEntity(alumnoService.listar(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/agregar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Alumno>> agregar(@RequestBody Alumno alumno)
	{
		alumnoService.agregar(alumno);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}