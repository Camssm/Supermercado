package com.app.Producto.service;

import java.util.List;

import com.app.Producto.entity.Producto;

public interface ProductoService {
	
    public List<Producto> listar();
    
    public void agregar(Producto producto);
}