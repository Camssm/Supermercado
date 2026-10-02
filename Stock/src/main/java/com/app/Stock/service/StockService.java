package com.app.Stock.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.Stock.entity.Stock;

public interface StockService {

public List<Stock> listar();
    
    public void agregar(Stock stock);
}
