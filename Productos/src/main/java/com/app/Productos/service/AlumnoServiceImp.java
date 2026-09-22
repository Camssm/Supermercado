package com.app.Productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Productos.entity.Alumno;
import com.app.Productos.repository.Alumnorepository;

@Service
public class AlumnoServiceImp implements AlumnoService {
	
	@Autowired
	Alumnorepository alumnorepository;
	
	@Override
	public List<Alumno> listar() {
		
		List<Alumno> registros = this.alumnorepository.findAll();
		return registros;
	}

	@Override
	public void agregar(Alumno alumno) {
		this.alumnorepository.save(alumno);
	}
	
}
