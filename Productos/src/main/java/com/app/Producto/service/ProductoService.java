package com.app.Producto.service;

import com.app.Producto.dto.ProductoDto;
import com.app.Producto.entity.Producto;
import com.app.Producto.mappers.IMapper;

import java.util.List;

public interface ProductoService {

    void agregar(IMapper<Producto> mapper) throws Exception;

    List<ProductoDto> listar() throws Exception;

    ProductoDto obtenerPorId(Long id) throws Exception;

    void editar(IMapper<Producto> mapper) throws Exception;

    void eliminar(Long id) throws Exception;

    void descontarStock(Long productoId, Integer cantidad);

    void ingresarStock(Long productoId, Integer cantidad);
}