package com.springboot.mini.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.springboot.mini.data.dto.WebtoonGenreRankDto;
import com.springboot.mini.data.entity.WebtoonGenre;

public interface WebtoonGenreRepository extends JpaRepository<WebtoonGenre, String>{
    // 장르에 해당하는 장르 코드 가져오는 쿼리문
    // 이후 컨트롤러에서 조인 테이블에서 값을 가져오는 파라미터 역할.
    @Query("Select g.genreCode FROM WebtoonGenre g where g.labelEnglish = :genre")
    String queryByGenreCode(String genre);

    // 뷰에 한글 장르 이름을 전달하기 위한 쿼리문
    @Query("Select g.labelKorea FROM WebtoonGenre g where g.labelEnglish = :genre")
    String queryByGenreKoran(String genre);

    // 웹툰랭크 테이블에 웹툰, 웹툰장르 테이블들을 조인하는 쿼리문
    @Query("SELECT new com.springboot.mini.data.dto.WebtoonGenreRankDto(" +
            "w.webtoonId, w.title, w.thumbnailUrl, t.genreRank) " +
            "FROM WebtoonRank t " +
            "inner JOIN WebtoonGenre g ON t.genreCode = g.genreCode " +
            "inner JOIN Webtoon w ON t.webtoonId = w.webtoonId " +
            "WHERE t.genreCode =:genreCode")
    List<WebtoonGenreRankDto> queryByGenreCodeWithWebtoonWithWebtoonRank(String genreCode);
}