package com.springboot.mini.data.repository;
import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.mini.data.entity.WebtoonReview;


public interface WebtoonReviewRepository extends JpaRepository<WebtoonReview, Long> {
    @Override
    ArrayList<WebtoonReview> findAll();
}
