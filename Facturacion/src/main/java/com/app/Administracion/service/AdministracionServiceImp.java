package com.app.Administracion.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Administracion.entity.Administracion;
import com.app.Administracion.repository.AdministracionRepository;

@Service
public class AdministracionServiceImp implements AdministracionService{

	@Autowired
	AdministracionRepository administracionRepository;
	
	@Override
	public List<Administracion> listar(){
		
		List<Administracion> registros = this.administracionRepository.findAll();
		return registros;
	}
	@Override
	public void agregar( Administracion administracion) {
		this.administracionRepository.save(administracion);
		
	}
}
