package com.app.Cursos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.Cursos.entity.Cursos;
import com.app.Cursos.service.CursosService;

@RestController
@RequestMapping(value = "/api/cursos")
public class CursosController {

		@Autowired
		private CursosService cursosService;
		
		@RequestMapping(value = "/cursos", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Cursos>> listar() 
		{
			return new ResponseEntity<>(cursosService.listar(), HttpStatus.OK);
		}
		
		@RequestMapping(value = "/agregar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<List<Cursos>> agregar(@RequestBody Cursos cursos)
		{
			cursosService.agregar(cursos);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
	}

