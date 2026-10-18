package com.app.Usuario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Usuario.entity.Usuario;
import com.app.Usuario.exception.UsuarioDuplicadoException;
import com.app.Usuario.repository.UsuarioRepository;

@Service
public class UsuarioServiceImp implements UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	public List<Usuario> listar() {
		return this.usuarioRepository.findAll();
	}

	@Override
	public void agregar(Usuario usuario) {
		if (usuarioRepository.existsByEmail(usuario.getEmail())) {
			throw new UsuarioDuplicadoException(usuario.getEmail());
		}

		this.usuarioRepository.save(usuario);
	}
}