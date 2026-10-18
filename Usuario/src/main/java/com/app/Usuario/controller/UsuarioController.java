package com.app.Usuario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.Usuario.entity.Usuario;
import com.app.Usuario.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(value = "/usuarios", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Usuario>> listar()
	{
		return new ResponseEntity<>(usuarioService.listar(), HttpStatus.OK);
	}

	@RequestMapping(value = "/agregar", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Void> agregar(@Valid @RequestBody Usuario usuario)
	{
		usuarioService.agregar(usuario);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}