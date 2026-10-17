package com.app.Producto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Producto.entity.Producto;
import com.app.Producto.repository.ProductoRepository;

@Service
public class ProductoServiceImp implements ProductoService {
	
	@Autowired
	ProductoRepository productoRepository;
	
	@Override
	public List<Producto> listar() {
		
		List<Producto> registros = this.productoRepository.findAll();
		return registros;
	}

	@Override
	public void agregar(Producto producto) {
		this.productoRepository.save(producto);
	}
	
}
