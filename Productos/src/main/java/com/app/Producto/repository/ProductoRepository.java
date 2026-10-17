package com.app.Producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Producto.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {

	
}
