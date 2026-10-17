package com.app.Usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Usuario.entity.Usuario;
import com.app.Usuario.repository.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public List<Usuario> listar() {

		List<Usuario> registros = this.usuarioRepository.findAll();
		return registros;
	}

	@Override
	public void agregar(Usuario usuario) {
		this.usuarioRepository.save(usuario);
	}

}