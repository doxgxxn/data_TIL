package com.springboot.mini.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.springboot.mini.Service.WebtoonService;
import com.springboot.mini.data.dto.WebtoonDto;
import com.springboot.mini.data.dto.WebtoonGenreRankDto;
import com.springboot.mini.data.dto.WebtoonReviewDto;
import com.springboot.mini.data.entity.WebtoonReview;
import com.springboot.mini.data.repository.WebtoonReviewRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WebtoonController {
    private final WebtoonService webtoonService;

    @Autowired
    private WebtoonReviewRepository webtoonReviewRepository;

    @Autowired
    public WebtoonController(WebtoonService webtoonService){
        this.webtoonService = webtoonService;
    }

    
    @RequestMapping("/webtoons")
    public RedirectView redirectToFirstPage() {
        return new RedirectView("/webtoons?page=1");
    }

    // 전체 조회(메인) 페이지 연결
    @GetMapping("/webtoons")
    public String index(Model model, @RequestParam(defaultValue = "1") int page){
        // 한 페이지에 12개 값을 전달
        int pageSize=12;

        // RequestParam을 default 1로 하고, PageRequest 에서 해당 page-1 을 통해
        // 페이지는 0부터 조회 가능하지만, 웹 페이지에 전달된 값은 1부터 시작하여
        // 페이지와 해당 값들의 부조화를 없애기 위한 것.
        Pageable pageable = PageRequest.of(page-1, pageSize);

        // log.info("나 여기 있어!!");

        // 1. 모든 데이터 가져오기
        Page<WebtoonDto> webtooonPage = webtoonService.getWebtoonAll(page-1, pageSize);
                                // index 0 값부터 페이지 전달 그러나 뷰에서는 1부터 전달.
                                
        log.info("PageNum : "+webtooonPage.getPageable());
        
        int first = 1;
        int totalPage = webtooonPage.getTotalPages();
        // log.info("토탈토탈 : "+totalPage);
        int nowPage = pageable.getPageNumber();
        log.info("나우 : "+nowPage);

        int beginPage = (nowPage / pageSize) * pageSize + 1;
        log.info("비긴 : "+beginPage);
        int endPage = Math.min(beginPage + pageSize -1, totalPage);

        int previous = Math.max(beginPage - pageSize, 1);

        log.info("previous : "+previous);

        int next = Math.min(endPage + 1, totalPage);

        log.info("next : "+next);

        ArrayList<Integer> pageIndex = new ArrayList<Integer>();
        
        for (int i = beginPage; i <= endPage; i++){
            pageIndex.add(i);
        }

        log.info("페이지 인덱스 :"+ pageIndex);
        // 2. 모델에 데이터 등록
        model.addAttribute("webtoonList", webtooonPage);
        model.addAttribute("first", first);
        model.addAttribute("last",totalPage);
        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("previous", previous);
        model.addAttribute("next", next);
        model.addAttribute("nowPage", nowPage);
        
        // 3. 뷰에 전송
        return "webtoons/main";
    }

    // 한 웹툰 선택 시 상세 내용 조회 페이지 연결
    @GetMapping("/webtoons/{webtoonId}")
    public String detail(@PathVariable Integer webtoonId, Model model){
        log.info("id 값은? : "+ webtoonId);
        
        List<WebtoonDto> webtoonDtos = webtoonService.getWebtoonByWebtoonId(webtoonId);
        log.info("Dto : "+webtoonDtos);

        // ,를 포함한 여러 문자열들이 합쳐진 문자열을
        // ,를 기준으로 리스트 나눠서 상세 페이지에 전달. -> 각 해시태그 마다 #를 붙이고 링크를 달기 위해.
        String hashTags= webtoonService.getHashTag(webtoonId);
        log.info("hass : "+hashTags);

        List<String> hashTagList = Arrays.asList(hashTags.split(","));

        log.info("해시태그 리스트 : "+hashTagList);
        

        model.addAttribute("webtoons", webtoonDtos);
        model.addAttribute("hashTagList", hashTagList);

        return "webtoons/detail";
    }

    // 검색 기능 페이지 연결
    @GetMapping("/webtoons/search/{every}")
    public String show(@PathVariable String every, Model model) {
        log.info("처음 every : "+every);
        if(every.contains("&")){
            every=every.replace("&", "/");
        }
        log.info("&를 /로 대체한 이후 : "+every);

        List<WebtoonDto> webtoonDtos = webtoonService.searchWebtoonsByEvery(every);
        
        model.addAttribute("webtoonList", webtoonDtos);
        model.addAttribute("every", every);
                
        return "webtoons/search";
    }
    


    @GetMapping("/webtoons/{genre}/rank")
    public String rankShow(@PathVariable String genre, Model model){
        String genreCode = webtoonService.getGenreCode(genre);
        String genreKorean = webtoonService.getGenreKorean(genre);
        List<WebtoonGenreRankDto> genreRankDtos = webtoonService.getJoinDtos(genreCode);
        
        log.info("장르 : "+genreKorean);
        log.info("장르 랭크 DTO : "+genreRankDtos);
        
        model.addAttribute("webtoonRankList", genreRankDtos);
        model.addAttribute("genreKorean", genreKorean);
        
        return "webtoons/rank_by_genre";
    }

    @GetMapping("/webtoons/reviews")
    public String reviewIndex(Model model){
        ArrayList<WebtoonReviewDto> webtoonReviewDtos = (ArrayList<WebtoonReviewDto>) webtoonService.getAllReviews();

        log.info("리뷰 디티오 : "+webtoonReviewDtos);
        model.addAttribute("reviewList", webtoonReviewDtos);

        return "reviews/all_review";
    }

    // 새 리뷰 작성 창
    @GetMapping("/webtoons/reviews/create/{webtoonId}")
    public String newReview(@PathVariable Integer webtoonId, Model model) {
        List<WebtoonDto> dto = webtoonService.getWebtoonByWebtoonId(webtoonId);
        
        model.addAttribute("webtoonData", dto);

        return "reviews/new";
    }

    // 리뷰 작성 후 게시
    @PostMapping("/webtoons/reviews/create/{webtoonId}")
    public String createReview(WebtoonReviewDto webtoonReviewDto, @PathVariable Integer webtoonId) {
        log.info("웹툰 아이디 : "+webtoonId);
        log.info("웹툰 이름 : "+webtoonService.getWebtoonTitle(webtoonId));

        WebtoonReview review = webtoonReviewDto.toEntity();

        review.setWebtoonId(webtoonId);
        review.setWebtoonTitle(webtoonService.getWebtoonTitle(webtoonId));
        review.setThumbnailUrl(webtoonService.getThumbnailUrl(webtoonId));
        log.info("저장 정보: " + review.toString());

        webtoonReviewRepository.save(review);
        
        return "redirect:/webtoons/reviews";
    }

    // 웹툰 리뷰에 대한 상세 페이지
    @GetMapping("/webtoons/reviews/show/{id}")
    public String showReview(@PathVariable Long id, Model model){
        WebtoonReviewDto webtoonReviewDto = webtoonService.getReviewOne(id);
        log.info("디테일 리뷰 : "+webtoonReviewDto);

        model.addAttribute("webtoonReviewList", webtoonReviewDto);

        return "reviews/review_detail";
    }


    @GetMapping("/webtoons/reviews/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        WebtoonReviewDto webtoonReviewDto = webtoonService.getReviewOne(id);
        log.info("웹툰 리뷰 엔티티 : "+webtoonReviewDto);

        model.addAttribute("reviews", webtoonReviewDto);

        return "reviews/edit";
    }

    @PostMapping("/webtoons/reviews/update/{id}")
    public String update(@PathVariable Long id, @ModelAttribute WebtoonReviewDto webtoonReviewDto) {
        WebtoonReview review = webtoonService.getReviewOne(id).toEntity();

        log.info("review 디티오 투 엔티티 : "+review);

        WebtoonReview target = webtoonReviewDto.toEntity();
        log.info("타겟 : "+target);

        if(target != null) {
            log.info("patch가 되냐?"+review.patch(target));

            webtoonReviewRepository.save(review.patch(target));
        }
        return "redirect:/webtoons/reviews/show/"+id;
    }

    @GetMapping("/webtoons/reviews/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        WebtoonReview target = webtoonReviewRepository.findById(id).orElse(null);

        if (target != null) {
            webtoonReviewRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제완료");
        }

        return "redirect:/webtoons/reviews";
    }

    @GetMapping("/webtoons/reviews/{webtoonId}")
    public String webtoonReviewList(@PathVariable Integer webtoonId, Model model){
        List<WebtoonReviewDto> webtoonReviewDtos = webtoonService.getReviewsByWebtoonId(webtoonId);
        String thumbnail = webtoonService.getThumbnailUrl(webtoonId);

        model.addAttribute("thisReviews", webtoonReviewDtos);
        model.addAttribute("thumbnail", thumbnail);
        
        return "reviews/review_list_by_webtoon";
    }

    @GetMapping("/webtoons/order/favorite/desc")
    public String webtoonOrderByFavoriteDESC(Model model){
        List<WebtoonDto> webtoonDtos = webtoonService.getWebtoonOrderByFavoriteDESC();

        model.addAttribute("favoriteDESC", webtoonDtos);

        return "webtoons/order_by_favorite_DESC";
    }

    @GetMapping("/webtoons/order/favorite/asc")
    public String webtoonOrderByFavoriteASC(Model model){
        List<WebtoonDto> webtoonDtos = webtoonService.getWebtoonOrderByFavoriteASC();

        model.addAttribute("favoriteASC", webtoonDtos);

        return "webtoons/order_by_favorite_ASC";
    }

    @GetMapping("/webtoons/publishDay/show/{weekDay}")
    public String webtoonWeekDayShow(@PathVariable String weekDay, Model model){
        List<WebtoonDto> webtoonDtos = webtoonService.getWebtoonByPublishDay(weekDay);
        log.info("위크데이 디티오 : "+webtoonDtos);

        Map<String, String> englishDay = new HashMap<>();

        // 요일을 맵에 추가합니다.
        englishDay.put("MONDAY", "월요일");
        englishDay.put("TUESDAY", "화요일");
        englishDay.put("WEDNESDAY", "수요일");
        englishDay.put("THURSDAY", "목요일");
        englishDay.put("FRIDAY", "금요일");
        englishDay.put("SATURDAY", "토요일");
        englishDay.put("SUNDAY", "일요일");
        englishDay.put("Finish", "연재 종료");

        String translatedDay = englishDay.get(weekDay);

        model.addAttribute("weekDay", translatedDay);
        model.addAttribute("webtoonList", webtoonDtos);

        return "webtoons/weekDayShow";
    }
    
}
