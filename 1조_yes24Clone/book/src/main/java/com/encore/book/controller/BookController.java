package com.encore.book.controller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.encore.book.data.dto.BookDto;
import com.encore.book.data.dto.BookInfoDto;
import com.encore.book.service.BookService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/books")
    public String index (Model model) {

        List<BookInfoDto> books = bookService.inquiry();

        model.addAttribute("bookList", books);
        return "books/index";
    }

    @GetMapping("/books/info")
    public String show(Model model, @RequestParam("id") String id) {

        log.info("id:::" + id);
        BookInfoDto bookInfo = bookService.getBookInfo(id);

        model.addAttribute("bookInfo", bookInfo);
        return "books/show";
    }

    @GetMapping("/books/page")
    public String page(Model model, @PageableDefault(sort = "rank", size=20, direction = Sort.Direction.ASC) Pageable pageable,
                    @RequestParam(value = "year", required = false) String year,
                    @RequestParam(value = "month", required = false) String month){
       
        if (year.equals(null) || year.isEmpty()) {
            System.out.println("year::::");
            year = "2023";
        }
        if (month.equals(null) || year.isEmpty()) {
            month = "8";
        }

        Page<BookDto> books = bookService.getBookInfoAndBookSellerAndPage(pageable, year, month);

        int pageSize = 10;

        int nowPage = pageable.getPageNumber() + 1;
        int totalPage = books.getTotalPages();
        int beginPage = ((nowPage-1) / pageSize) * pageSize + 1;
        int endPage = Math.min(beginPage + pageSize -1, totalPage);

        int previous = Math.max(beginPage - pageSize, 1);
        int next = Math.min(endPage + 1, totalPage);

        ArrayList<Integer> pageIndex = new ArrayList<Integer>();
        for (int i = beginPage; i <= endPage; i++){
            pageIndex.add(i);
        }

        log.info("beginPage:::" + beginPage);
        log.info("totalPage:::" + totalPage);

        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("previous", previous);
        model.addAttribute("next", next);
        model.addAttribute("nowPage", nowPage);

        model.addAttribute("year", year);
        model.addAttribute("month", month);

        model.addAttribute("totalPage", books.getTotalPages());
        model.addAttribute("bookList", books);
        return "books/index";
    }

    @GetMapping("/books/index")
    public String page(Model model, @PageableDefault(sort = "rank", size=20, direction = Sort.Direction.ASC) Pageable pageable){
       
        String year = "2023";
        String month = "8";

        Page<BookDto> books = bookService.getBookInfoAndBookSellerAndPage(pageable, year, month);

        int pageSize = 10;

        int nowPage = pageable.getPageNumber() + 1;
        int totalPage = books.getTotalPages();
        int beginPage = ((nowPage-1) / pageSize) * pageSize + 1;
        int endPage = Math.min(beginPage + pageSize -1, totalPage);

        int previous = Math.max(beginPage - pageSize, 1);
        int next = Math.min(endPage + 1, totalPage);

        ArrayList<Integer> pageIndex = new ArrayList<Integer>();
        for (int i = beginPage; i <= endPage; i++){
            pageIndex.add(i);
        }

        log.info("beginPage:::" + beginPage);
        log.info("endPage" + endPage);
        log.info("totalPage:::" + totalPage);

        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("previous", previous);
        model.addAttribute("next", next);
        model.addAttribute("nowPage", nowPage);

        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("totalPage", books.getTotalPages());

        model.addAttribute("bookList", books);
        return "books/index";
    }

    @GetMapping("/books/search")
    public String search(
        Model model,
        Pageable pageable,
        @RequestParam String search, // 검색 텍스트
        @RequestParam(value = "searchOption") String searchOption // 검색 옵션
    ) {
        Page<BookInfoDto> books;

        try {
            // BookService 클래스에서 메서드 이름을 동적으로 생성
            String methodName = "getSearchBookListBy" + searchOption;
            Method method = BookService.class.getMethod(methodName, Pageable.class, String.class);

            books = (Page<BookInfoDto>) method.invoke(bookService, pageable, search);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            books = bookService.getSearchBookListByAll(pageable, search); // 기본적으로 "전체 검색" 메서드 호출
        }
        

        int pageSize = 10;

        int nowPage = pageable.getPageNumber() + 1;
        int totalPage = books.getTotalPages();
        int beginPage = ((nowPage-1) / pageSize) * pageSize + 1;
        int endPage = Math.min(beginPage + pageSize -1, totalPage);

        int previous = Math.max(beginPage - pageSize, 1);
        int next = Math.min(endPage + 1, totalPage);

        ArrayList<Integer> pageIndex = new ArrayList<Integer>();
        for (int i = beginPage; i <= endPage; i++){
            pageIndex.add(i);
        }

        log.info("beginPage:::" + beginPage);
        log.info("endPage" + endPage);
        log.info("totalPage:::" + totalPage);

        model.addAttribute("pageIndex", pageIndex);
        model.addAttribute("previous", previous);
        model.addAttribute("next", next);
        model.addAttribute("nowPage", nowPage);

        model.addAttribute("totalPage", books.getTotalPages());
        
        model.addAttribute("searchOption", searchOption);
        model.addAttribute("search", search);
        model.addAttribute("bookList", books);

        return "books/search";
    }

}
