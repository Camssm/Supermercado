package com.app.Productos.service;

import java.util.List;

import com.app.Productos.entity.Productos;

public interface ProductosService {
	
    public List<Productos> listar();
    
    public void agregar(Productos productos);
}