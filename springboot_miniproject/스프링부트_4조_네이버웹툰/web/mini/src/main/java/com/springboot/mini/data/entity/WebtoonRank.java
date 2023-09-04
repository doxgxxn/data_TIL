package com.springboot.mini.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "toonrank")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WebtoonRank {
    @Id
    private Long numb;

    @Column(name = "genre_code")
    private String genreCode;

    @Column(name = "webtoon_id")
    private Integer webtoonId;

    @Column(name = "genre_rank")
    private Integer genreRank;

}
