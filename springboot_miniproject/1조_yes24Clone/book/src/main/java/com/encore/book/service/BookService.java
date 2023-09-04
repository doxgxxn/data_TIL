package com.encore.book.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.encore.book.data.dto.BestSellerDto;
import com.encore.book.data.dto.BookDto;
import com.encore.book.data.dto.BookInfoDto;

public interface BookService {

    List<BookInfoDto> inquiry();

    List<BestSellerDto> seletBestSeller();

    BookInfoDto getBookInfo(String id);

    Page<BookDto> getBookInfoAndBookSellerAndPage(Pageable pageable, String year, String month);

    Page<BookInfoDto> getSearchBookListByAuthor(Pageable pageable, String search);
    Page<BookInfoDto> getSearchBookListByName(Pageable pageable, String search);
    Page<BookInfoDto> getSearchBookListByPublisher(Pageable pageable, String search);
    Page<BookInfoDto> getSearchBookListByAll(Pageable pageable, String search);
}
