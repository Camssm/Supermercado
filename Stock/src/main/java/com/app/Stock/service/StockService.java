package com.app.Stock.service;

import java.util.List;

import com.app.Stock.entity.Stock;

public interface StockService {

    public List<Stock> listar();

    public void agregar(Stock stock);
}