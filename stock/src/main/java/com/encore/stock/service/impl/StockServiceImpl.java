package com.encore.stock.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.encore.stock.data.dto.StockDto;
import com.encore.stock.data.entity.Stock;
import com.encore.stock.data.repository.StockRepository;
import com.encore.stock.service.StockService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService{
    private StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository){
        this.stockRepository = stockRepository;
    }

    @Override
    public List<StockDto> getStockByStockCode(String stockCode) {
        List<Stock> stocks = stockRepository.queryByStockCode(stockCode);
        return stocks.stream().map(this::convertToDTO).collect(Collectors.toList());
    }


    private StockDto convertToDTO(Stock stock){
        StockDto dto = new StockDto();
        dto.setStockCode(stock.getStockCode());
        dto.setDate(stock.getDate());
        dto.setOpen(stock.getOpen());
        dto.setHigh(stock.getHigh());
        dto.setLow(stock.getLow());
        dto.setClose(stock.getClose());
        dto.setVolume(stock.getVolume());
        dto.setForeignExchangeRate(stock.getForeignExchangeRate());
        return dto;
    }
}
