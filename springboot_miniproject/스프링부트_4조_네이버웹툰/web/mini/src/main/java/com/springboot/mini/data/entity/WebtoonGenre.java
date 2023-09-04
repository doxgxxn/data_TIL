package com.springboot.mini.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="genre")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WebtoonGenre{
    @Id
    private Long numb;

    @Column(name = "genre_code")
    private String genreCode;

    @Column(name = "label_korea")
    private String labelKorea;

    @Column(name = "genre_kind")
    private String genreKind;

    @Column(name = "label_english")
    private String labelEnglish;

}
