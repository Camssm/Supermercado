package com.app.Facturacion.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Facturacion.entity.Facturacion;
import com.app.Facturacion.repository.FacturacionRepository;

@Service
public class FacturacionServiceImp implements FacturacionService{

	@Autowired
	FacturacionRepository facturacionRepository;
	
	@Override
	public List<Facturacion> listar(){
		
		List<Facturacion> registros = this.facturacionRepository.findAll();
		return registros;
	}
	@Override
	public void agregar( Facturacion facturacion) {
		this.facturacionRepository.save(facturacion);
		
	}
}
