package com.app.Stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.Stock.entity.Stock;


public interface StockRepository extends JpaRepository<Stock, Integer>  {

}
