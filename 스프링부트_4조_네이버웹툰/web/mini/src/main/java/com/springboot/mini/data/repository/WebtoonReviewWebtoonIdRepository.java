package com.springboot.mini.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.mini.data.entity.WebtoonReview;

public interface WebtoonReviewWebtoonIdRepository extends JpaRepository<WebtoonReview, Integer> {

    @Query("SELECT wr FROM WebtoonReview wr WHERE wr.webtoonId =:webtoonId")
    List<WebtoonReview> queryByWebtoonId(Integer webtoonId);
}
