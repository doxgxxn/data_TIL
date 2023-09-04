package com.springboot.mini.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WebtoonGenreDto {
    private Long numb;
    private String genreCode;
    private String labelKorea;
    private String genreKind;
    private String labelEnglish;
}
