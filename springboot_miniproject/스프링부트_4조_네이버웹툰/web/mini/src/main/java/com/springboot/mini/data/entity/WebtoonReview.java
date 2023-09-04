package com.springboot.mini.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WebtoonReview {
    // 리뷰 게시글 번호 (자동 생성)
    @Id
    @GeneratedValue
    private Long id;

    // 웹툰 Id
    @Column
    private Integer webtoonId;

    // 웹툰 제목
    @Column
    private String webtoonTitle;

    @Column
    private String thumbnailUrl;

    // 게시글 제목
    @Column
    private String title;
    
    // 게시글 내용
    @Column
    private String content;

    public WebtoonReview patch(WebtoonReview review) {
        if (review.title != null) {
            this.title = review.title;
        }
        if (review.content != null) {
            this.content = review.content;
        }

        return review;
    }

    public WebtoonReview toEntity(){
        return new WebtoonReview(id, webtoonId, webtoonTitle, thumbnailUrl,title, content);
    }
}
