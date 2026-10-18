package com.app.Producto.service;

import com.app.Producto.dto.ProductoDto;
import com.app.Producto.entity.Producto;
import com.app.Producto.exception.ProductoNoEncontradoException;
import com.app.Producto.exception.StockInsuficienteException;
import com.app.Producto.mappers.IMapper;
import com.app.Producto.repository.ProductoRepository;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImp implements ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoServiceImp(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public void agregar(IMapper<Producto> mapper) throws Exception {
        Producto p = mapper.mapperTo();

        if (p == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        if (p.getNombre() == null || p.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (p.getPrecioUnitario() == null ||
                p.getPrecioUnitario().signum() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        if (p.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        productoRepository.save(p);
    }

    @Override
    public List<ProductoDto> listar() throws Exception {
        return productoRepository.findAll().stream()
                .map(p -> new ProductoDto(
                        p.getId(),
                        p.getNombre(),
                        p.getCodigoDeBarras(),
                        p.getCategoria(),
                        p.getDescripcion(),
                        p.getMarca(),
                        p.getPrecioUnitario(),
                        p.getImagenUrl(),
                        p.getStock()))
                .collect(Collectors.toList());
    }

    @Override
    public ProductoDto obtenerPorId(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de producto inválido");
        }

        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNoEncontradoException(id));

        return new ProductoDto(
                p.getId(),
                p.getNombre(),
                p.getCodigoDeBarras(),
                p.getCategoria(),
                p.getDescripcion(),
                p.getMarca(),
                p.getPrecioUnitario(),
                p.getImagenUrl(),
                p.getStock());
    }

    @Override
    public void editar(IMapper<Producto> mapper) throws Exception {
        Producto datos = mapper.mapperTo();

        if (datos == null || datos.getId() <= 0) {
            throw new IllegalArgumentException(
                    "Se necesita un ID válido para editar");
        }

        Producto existente = productoRepository.findById(datos.getId())
                .orElseThrow(() -> new ProductoNoEncontradoException(datos.getId()));

        if (datos.getNombre() == null || datos.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (datos.getPrecioUnitario() == null ||
                datos.getPrecioUnitario().signum() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }

        // Actualizamos los datos del producto existente.
        existente.setNombre(datos.getNombre());
        existente.setCodigoDeBarras(datos.getCodigoDeBarras());
        existente.setCategoria(datos.getCategoria());
        existente.setDescripcion(datos.getDescripcion());
        existente.setMarca(datos.getMarca());
        existente.setPrecioUnitario(datos.getPrecioUnitario());
        existente.setImagenUrl(datos.getImagenUrl());

        // El stock se gestiona por separado.
        productoRepository.save(existente);
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de producto inválido");
        }

        if (!productoRepository.existsById(id)) {
            throw new ProductoNoEncontradoException(id);
        }

        productoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void descontarStock(Long productoId, Integer cantidad) {
        if (productoId == null || productoId <= 0) {
            throw new IllegalArgumentException("ID de producto inválido");
        }

        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad a descontar debe ser mayor que cero");
        }

        Producto p = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException(productoId));

        if (p.getStock() < cantidad) {
            throw new StockInsuficienteException(p.getNombre());
        }

        p.setStock(p.getStock() - cantidad);
        productoRepository.save(p);
    }

    @Override
    @Transactional
    public void ingresarStock(Long productoId, Integer cantidad) {
        if (productoId == null || productoId <= 0) {
            throw new IllegalArgumentException("ID de producto inválido");
        }

        if (cantidad == null || cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad a ingresar debe ser mayor que cero");
        }

        Producto p = productoRepository.findById(productoId)
                .orElseThrow(() -> new ProductoNoEncontradoException(productoId));

        p.setStock(p.getStock() + cantidad);
        productoRepository.save(p);
    }
}