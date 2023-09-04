package com.springboot.mini.data.dto;

import com.springboot.mini.data.entity.WebtoonReview;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WebtoonReviewDto {
    private Long id;
    private Integer webtoonId;
    private String webtoonTitle;
    private String thumbnailUrl;
    private String title; // 리뷰 제목
    private String content; // 리뷰 내용

    public WebtoonReview toEntity() {
        return new WebtoonReview(id, webtoonId, webtoonTitle,thumbnailUrl, title, content);
    }
}
