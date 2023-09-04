package com.encore.book.data.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class BookInfo {

    @Id
    private String id;
    private String name;
    private String author;
    private String publisher;
    private String date;
    private String price;
    private String genre;
    private String intro;
    private String starpoint;
    
    // @OneToOne(mappedBy = "bookInfo")
    // private BestSeller bestSeller;
}
