package com.springboot.mini.data.dto;

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
public class WebtoonGenreRankDto {
    private Integer webtoonId;
    private String title;
    private String thumbnailUrl;
    private Integer webtoonRank;

}
