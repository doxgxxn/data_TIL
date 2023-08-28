package com.encore.stock.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDto {
    private String stockCode;
    private String date;
    private Integer open;
    private Integer high;
    private Integer low;
    private Integer close;
    private Integer volume;
    private Float foreignExchangeRate;
}
