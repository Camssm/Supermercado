package com.app.Usuario.service;

import java.util.List;

import com.app.Usuario.entity.Usuario;

public interface UsuarioService {

	public List<Usuario> listar();

	public void agregar(Usuario usuario);
}