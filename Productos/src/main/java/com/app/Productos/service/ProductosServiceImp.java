package com.app.Productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Productos.entity.Productos;
import com.app.Productos.repository.ProductosRepository;

@Service
public class ProductosServiceImp implements ProductosService {
	
	@Autowired
	ProductosRepository productosRepository;
	
	@Override
	public List<Productos> listar() {
		
		List<Productos> registros = this.productosRepository.findAll();
		return registros;
	}

	@Override
	public void agregar(Productos productos) {
		this.productosRepository.save(productos);
	}
	
}
