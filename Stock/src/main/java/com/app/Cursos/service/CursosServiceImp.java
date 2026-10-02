package com.app.Cursos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Cursos.entity.Cursos;
import com.app.Cursos.repository.CursosRepository;

@Service
public class CursosServiceImp implements CursosService{

	@Autowired
	CursosRepository cursosRepository;
	
	@Override
	public List<Cursos> listar() {
		
		List<Cursos> registros = this.cursosRepository.findAll();
		return registros;
	}

	@Override
	public void agregar(Cursos cursos) {
        this.cursosRepository.save(cursos);
	}
}
