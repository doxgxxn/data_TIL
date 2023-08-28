package com.encore.stock.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.encore.stock.service.StockService;
import com.encore.stock.data.dto.StockDto;
import java.util.List;

@RestController
public class StockController {
    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService){
        this.stockService = stockService;
    }

    @GetMapping("/stock/{code}")
    public ResponseEntity<List<StockDto>> show(@PathVariable String code){
        List<StockDto> stockDtos = stockService.getStockByStockCode(code);
        if(stockDtos.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(stockDtos);
        }
    }
}
