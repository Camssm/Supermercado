package com.app.Stock.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Stock.entity.Stock;
import com.app.Stock.repository.StockRepository;

@Service
public class StockServiceImp implements StockService {

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> listar() {
        return this.stockRepository.findAll();
    }

    @Override
    public void agregar(Stock stock) {
        if (stock.getFechaActualizacion() == null) {
            stock.setFechaActualizacion(LocalDateTime.now());
        }
        this.stockRepository.save(stock);
    }
}