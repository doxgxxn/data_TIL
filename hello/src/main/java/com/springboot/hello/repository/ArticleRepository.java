package com.springboot.hello.repository;

import org.springframework.data.repository.CrudRepository;
import com.springboot.hello.entity.Article;

public interface ArticleRepository extends CrudRepository<Article, Long>{
    
}
