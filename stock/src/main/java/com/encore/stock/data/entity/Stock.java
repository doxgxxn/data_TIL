package com.encore.stock.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Stock {
    @Id
    private Long numb;

    @Column(name = "Stock_code")
    private String stockCode;

    @Column(name = "Date")
    private String date;

    @Column(name = "Open")
    private Integer open;

    @Column(name = "High")
    private Integer high;

    @Column(name = "Low")
    private Integer low;

    @Column(name = "Close")
    private Integer close;

    @Column(name = "Volume")    
    private Integer volume;

    @Column(name = "Foreign_ex_rate")
    private Float foreignExchangeRate;

}