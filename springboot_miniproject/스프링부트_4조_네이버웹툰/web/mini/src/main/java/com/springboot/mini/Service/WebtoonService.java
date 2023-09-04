package com.springboot.mini.Service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.springboot.mini.data.dto.WebtoonDto;
import com.springboot.mini.data.dto.WebtoonGenreRankDto;
import com.springboot.mini.data.dto.WebtoonReviewDto;

public interface WebtoonService {
    // 전체 조회
    Page<WebtoonDto> getWebtoonAll(int page, int pageSize);

    // 상세 조회
    List<WebtoonDto> getWebtoonByWebtoonId(Integer webtoonId);

    // 상세 페이지에 해시태그를 전달하기 위해
    String getHashTag(Integer webtoonId);

    // 검색
    List<WebtoonDto> searchWebtoonsByEvery(String every);

    // Join으로 연결된 테이블에 genre code를 전달하기 위해
    String getGenreCode(String genre);

    // 랭킹 페이지에 한글 장르를 전달하기 위해
    String getGenreKorean(String genre);

    // 랭킹 테이블을 통해 순위 데이터를 보여주는 기능
    List<WebtoonGenreRankDto> getJoinDtos(String code);

    // 모든 리뷰를 가져오는 기능
    List<WebtoonReviewDto> getAllReviews();

    // 한 리뷰에 대한 상세 페이지 기능
    WebtoonReviewDto getReviewOne(Long id);

    // 리뷰 테이블에 담을 웹툰 제목을 가져오는 기능
    String getWebtoonTitle(Integer webtoonId);

    // 리뷰 테이블에 담을 웹툰 썸네일 url을 가져오는 기능
    String getThumbnailUrl(Integer webtoonId);

    // 리뷰 테이블에서 웹툰ID 값으로 검색하여 해당하는 웹툰의 리뷰들을 가져오는 기능
    List<WebtoonReviewDto> getReviewsByWebtoonId(Integer webtoonId);

    // 웹툰을 관심도 수에 따라 내림차순, 오름차순으로 표시
    List<WebtoonDto> getWebtoonOrderByFavoriteDESC ();
    List<WebtoonDto> getWebtoonOrderByFavoriteASC ();

    // 연재 요일에 따른 웹툰 가져오기
    List<WebtoonDto> getWebtoonByPublishDay(String publishDay);
}
