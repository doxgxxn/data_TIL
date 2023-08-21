package com.springboot.hello.controller;
import com.springboot.hello.dto.ArticleFrom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.springboot.hello.entity.Article;
import com.springboot.hello.repository.ArticleRepository;

@Controller
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @GetMapping("/articles/new")
    public String newAriticleForm(){
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleFrom form){
        System.out.println(form.toString());
        Article article = form.toEntity();
        Article saved = articleRepository.save(article);
        System.out.println(saved.toString());
        return "";
    }

}
