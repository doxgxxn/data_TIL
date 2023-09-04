package com.encore.book.data.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

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
public class BestSeller {

    @Id
    private String id;
    private String year;
    private String month;
    private int rank;

    
    // @OneToOne
    // @JoinColumn(name = "id") // 외래 키 컬럼 이름
    // private BookInfo bookInfo;
}
