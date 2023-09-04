package com.springboot.mini.Service.impl;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.springboot.mini.Service.WebtoonService;
import com.springboot.mini.data.dto.WebtoonDto;
import com.springboot.mini.data.dto.WebtoonGenreRankDto;
import com.springboot.mini.data.dto.WebtoonReviewDto;
import com.springboot.mini.data.entity.Webtoon;
import com.springboot.mini.data.entity.WebtoonReview;
import com.springboot.mini.data.repository.WebtoonGenreRepository;
import com.springboot.mini.data.repository.WebtoonRepository;
import com.springboot.mini.data.repository.WebtoonReviewRepository;
import com.springboot.mini.data.repository.WebtoonReviewWebtoonIdRepository;
import com.springboot.mini.data.repository.WebtoonSearchRepository;

@Service
public class WebtoonServiceImpl implements WebtoonService{
    
    private WebtoonRepository webtoonRepository;
    private WebtoonSearchRepository webtoonSearchRepository;
    private WebtoonGenreRepository webtoonGenreRepository;
    private WebtoonReviewRepository webtoonReviewRepository;
    private WebtoonReviewWebtoonIdRepository webtoonReviewWebtoonIdRepository;

    @Autowired
    public WebtoonServiceImpl(WebtoonRepository webtoonRepository, WebtoonSearchRepository webtoonSearchRepository
    , WebtoonGenreRepository webtoonGenreRepository, WebtoonReviewRepository webtoonReviewRepository
    , WebtoonReviewWebtoonIdRepository webtoonReviewWebtoonIdRepository){

        this.webtoonRepository = webtoonRepository;
        this.webtoonSearchRepository = webtoonSearchRepository;
        this.webtoonGenreRepository = webtoonGenreRepository;
        this.webtoonReviewRepository = webtoonReviewRepository;
        this.webtoonReviewWebtoonIdRepository = webtoonReviewWebtoonIdRepository;
    }

    // 전체 조회
    @Override
    public Page<WebtoonDto> getWebtoonAll(int page, int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Webtoon> webtoonPage = webtoonRepository.findAll(pageable);

        // List<Webtoon> webtoons = webtoonRepository.findAll();
        // return webtoons.stream().map(this::convertToDTO).collect(Collectors.toList());
        return webtoonPage.map(this::convertToDTO);
    }


    // 웹툰 상세 페이지
    @Override
    public List<WebtoonDto> getWebtoonByWebtoonId(Integer webtoonId){
        List<Webtoon> webtoons = webtoonRepository.queryByWebtoonId(webtoonId);
        return webtoons.stream().map(this::convertToDTO2).collect(Collectors.toList());
    }

    // 해시태그 전달
    @Override
    public String getHashTag(Integer webtoonId){
        String hashTagList = webtoonRepository.queryByWebtoonIdHashTag(webtoonId);
        return hashTagList;
    }

    // 검색
    @Override
    public List<WebtoonDto> searchWebtoonsByEvery(String every) {
        List<Webtoon> webtoons = webtoonSearchRepository.queryByEvery(every);
        return webtoons.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
    
    // 장르 코드 전달
    @Override
    public String getGenreCode(String genre) {
        String genreCode = webtoonGenreRepository.queryByGenreCode(genre);
        return genreCode;
    }

    // 장르 한글 명 전달
    @Override
    public String getGenreKorean(String genre) {
        String genreKorean = webtoonGenreRepository.queryByGenreKoran(genre);
        return genreKorean;
    }

    // 장르별 TOP10 랭크 전달
    @Override
    public List<WebtoonGenreRankDto> getJoinDtos(String code){
        List<WebtoonGenreRankDto> genreRanks = webtoonGenreRepository.queryByGenreCodeWithWebtoonWithWebtoonRank(code);
        return genreRanks;
    }

    //전체 리뷰를 dto로 가져옴
    @Override
    public ArrayList<WebtoonReviewDto> getAllReviews(){
        ArrayList<WebtoonReview> reviews = webtoonReviewRepository.findAll();

        return reviews.stream().map(this::convertReviewToDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    // 선택한 리뷰의 상세 내용을 가져오는 기능
    @Override
    public WebtoonReviewDto getReviewOne(Long id){
        WebtoonReview reviews = webtoonReviewRepository.findById(id).orElse(null);

        return convertReviewToDTO(reviews);
    }

    // 리뷰 테이블에 삽입할 웹툰 제목 문자열 반환
    @Override
    public String getWebtoonTitle(Integer webtoonId){
        String webtoonTitle = webtoonRepository.queryByWebtoonIdWebtoonTitle(webtoonId);

        return webtoonTitle;
    }

    // 리뷰 테이블에 삽입할 웹툰 썸네일 url 문자열 반환
    @Override
    public String getThumbnailUrl(Integer webtoonId){
        String thumbnailUrl = webtoonRepository.queryByWebtoonIdThumbnailUrl(webtoonId);

        return thumbnailUrl;
    }

    // 웹툰 ID에 따른 모든 리뷰 가져오는 기능
    @Override
    public List<WebtoonReviewDto> getReviewsByWebtoonId(Integer webtoonId){
        List<WebtoonReview> reviews = webtoonReviewWebtoonIdRepository.queryByWebtoonId(webtoonId);

        return reviews.stream().map(this::convertReviewToDTO).collect(Collectors.toList());
    }

    // 관심도 내림차순으로 정렬
    @Override
    public List<WebtoonDto> getWebtoonOrderByFavoriteDESC(){
        List<Webtoon> webtoons = webtoonRepository.queryOrderByFavoriteDESC();

        return webtoons.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // 관심도 오름차순으로 정렬
    @Override
    public List<WebtoonDto> getWebtoonOrderByFavoriteASC(){
        List<Webtoon> webtoons = webtoonRepository.queryOrderByFavoriteASC();

        return webtoons.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<WebtoonDto> getWebtoonByPublishDay(String publishDay){
        List<Webtoon> webtoons = webtoonSearchRepository.queryByPublishDay(publishDay);

        return webtoons.stream().map(this::convertToDTO2).collect(Collectors.toList());
    }

    //-------- Service 내에서만 활용되는 함수들 -----------------------

    private WebtoonDto convertToDTO(Webtoon webtoon){
        WebtoonDto dto = new WebtoonDto();
        dto.setWebtoonId(webtoon.getWebtoonId());
        dto.setTitle(webtoon.getTitle());
        dto.setThumbnailUrl(webtoon.getThumbnailUrl());
        dto.setHashTag(webtoon.getHashTag());
        dto.setFavorite(webtoon.getFavorite().toString());
        
        dto = changeFavoriteFormatting(dto);
        
        return dto;
    }

    
    private WebtoonDto convertToDTO2(Webtoon webtoon){
        WebtoonDto dto = new WebtoonDto();
        dto.setWebtoonId(webtoon.getWebtoonId());
        dto.setTitle(webtoon.getTitle());
        dto.setWriter(webtoon.getWriter());
        dto.setPainter(webtoon.getPainter());
        dto.setAge(webtoon.getAge());
        dto.setFavorite(webtoon.getFavorite().toString());
        dto.setStarScore(webtoon.getStarScore());
        dto.setThumbnailUrl(webtoon.getThumbnailUrl());
        dto.setPublishDay(webtoon.getPublishDay());
        dto.setHashTag(webtoon.getHashTag());

        dto = changeAgeToKorean(dto);
        dto = changePublishdayToKorean(dto);
        dto = changeFavoriteFormatting(dto);

        return dto;
    }

    private WebtoonReviewDto convertReviewToDTO(WebtoonReview webtoonReview){
        WebtoonReviewDto dto = new WebtoonReviewDto();
        dto.setId(webtoonReview.getId());
        dto.setTitle(webtoonReview.getTitle());
        dto.setContent(webtoonReview.getContent());
        dto.setWebtoonId(webtoonReview.getWebtoonId());
        dto.setWebtoonTitle(webtoonReview.getWebtoonTitle());
        dto.setThumbnailUrl(webtoonReview.getThumbnailUrl());
        
        return dto;
    }

    // 시청 연령 제한을 한글 표기로 바꿈.
    private WebtoonDto changeAgeToKorean(WebtoonDto webtoonDto){
        String age = webtoonDto.getAge();

        // 시청 연령 제한 표시 이름 바꾸기
        if("RATE_15".equals(age)){
            webtoonDto.setAge("15세 이용가");
        }else if("RATE_18".equals(age)){
            webtoonDto.setAge("18세 이용가, 성인물");
        }else if("RATE_12".equals(age)){
            webtoonDto.setAge("12세 이용가");
        }else{
            webtoonDto.setAge("전체 이용가");
        }

        return webtoonDto;
    }
    
    // 영어 연재일을 한글로 바꿈
    private WebtoonDto changePublishdayToKorean(WebtoonDto webtoonDto){
        String publishDay = webtoonDto.getPublishDay();

        // HashMap을 사용하여 요일을 번역하는 맵을 생성합니다.
        Map<String, String> weekDay = new HashMap<>();

        // 요일을 맵에 추가합니다.
        weekDay.put("MONDAY", "월요일");
        weekDay.put("TUESDAY", "화요일");
        weekDay.put("WEDNESDAY", "수요일");
        weekDay.put("THURSDAY", "목요일");
        weekDay.put("FRIDAY", "금요일");
        weekDay.put("SATURDAY", "토요일");
        weekDay.put("SUNDAY", "일요일");
        weekDay.put("Finish", "연재 종료");

        // 연재 요일을 한글로 바꾸기
        if(publishDay.length()>9){
            String[] dayArray = publishDay.split(",");

            StringBuilder translatedDay = new StringBuilder();

            for (String day : dayArray) {
                String translated = weekDay.get(day);
                if (translated != null) {
                    translatedDay.append(translated);
                    translatedDay.append(",");
                }
            }

            // 마지막 쉼표 제거
            if (translatedDay.length() > 0) {
                translatedDay.setLength(translatedDay.length() - 1);
            }

            webtoonDto.setPublishDay(translatedDay.toString());
        }else{
            String translatedDay = weekDay.get(publishDay);
            webtoonDto.setPublishDay(translatedDay.toString());
        }

        return webtoonDto;
    }

    // 관심도 숫자를 #,### 형식화로 표기
    private WebtoonDto changeFavoriteFormatting(WebtoonDto webtoonDto){
        String favor = webtoonDto.getFavorite();

        // 관심도 수를 자릿수마다 ',' 로 형식화
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        try{
            Number parsedNumber = decimalFormat.parse(favor);
            String formattedNum = decimalFormat.format(parsedNumber);
            webtoonDto.setFavorite(formattedNum);

        }catch(ParseException pe){
            pe.printStackTrace();
        }
        
        return webtoonDto;
    }

}


