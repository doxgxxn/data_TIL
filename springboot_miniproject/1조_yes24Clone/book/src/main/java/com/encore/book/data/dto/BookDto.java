package com.encore.book.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BookDto {

    private String id;
    private String name;
    private String author;
    private String publisher;
    private String date;
    private String price;
    private String genre;
    private String intro;
    private String starpoint;
    private String year;
    private String month;
    private int rank;
    
    private double price2;
    private double priceSale;
}
