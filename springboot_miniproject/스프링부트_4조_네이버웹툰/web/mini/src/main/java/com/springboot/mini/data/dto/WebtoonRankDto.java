package com.springboot.mini.data.dto;

import com.springboot.mini.data.entity.Webtoon;
import com.springboot.mini.data.entity.WebtoonGenre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WebtoonRankDto {
    private Long numb;
    private Integer genreRank;
    private WebtoonGenre genre;
    private Webtoon webtoon;
}
