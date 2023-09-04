package com.springboot.mini.data.repository;
// 리포지토리는 엔티티가 생성한 DB에 접근하는 데 사용되는 인터페이스

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.mini.data.entity.Webtoon;

public interface WebtoonRepository extends JpaRepository<Webtoon, Integer>{
    // 웹툰 id에 따른 상세 컬럼들을 가져오는 쿼리
    @Query("SELECT w FROM Webtoon w WHERE w.webtoonId = :webtoonId")
    List<Webtoon> queryByWebtoonId(Integer webtoonId);

    // 선택한 웹툰의 해시태그를 가져오는 쿼리
    @Query("SELECT w.hashTag FROM Webtoon w WHERE w.webtoonId = :webtoonId")
    String queryByWebtoonIdHashTag(Integer webtoonId);

    @Query("SELECT w.title FROM Webtoon w WHERE w.webtoonId = :webtoonId")
    String queryByWebtoonIdWebtoonTitle(Integer webtoonId);

    @Query("SELECT w.thumbnailUrl FROM Webtoon w WHERE w.webtoonId = :webtoonId")
    String queryByWebtoonIdThumbnailUrl(Integer webtoonId);

    @Query("SELECT w FROM Webtoon w ORDER BY w.favorite DESC")
    List<Webtoon> queryOrderByFavoriteDESC();

    @Query("SELECT w FROM Webtoon w ORDER BY w.favorite ASC")
    List<Webtoon> queryOrderByFavoriteASC();

}
