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
public class BestSellerDto {

    private String id;    
    private String year;
    private String month;
    private String rank;
    
}
