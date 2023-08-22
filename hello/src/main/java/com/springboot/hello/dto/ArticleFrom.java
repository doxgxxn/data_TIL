package com.springboot.hello.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;
import com.springboot.hello.entity.Article;

@AllArgsConstructor
@ToString
public class ArticleFrom {
    private Long id;
    private String title; // 제목 
    private String content; // 내용

    public Article toEntity(){
        return new Article(id, title, content);
    }
    // public ArticleFrom(String title, String content){
    //     this.title = title;
    //     this.content = content;
    // }
}
