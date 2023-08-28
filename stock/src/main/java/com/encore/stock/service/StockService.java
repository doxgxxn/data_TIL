package com.encore.stock.service;

import com.encore.stock.data.dto.StockDto;
import java.util.List;

public interface StockService {
    List<StockDto> getStockByStockCode(String stockCode);    
}
