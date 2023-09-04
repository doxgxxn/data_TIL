package com.encore.book.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.encore.book.data.dto.BestSellerDto;
import com.encore.book.data.dto.BookDto;
import com.encore.book.data.dto.BookInfoDto;
import com.encore.book.data.entity.BestSeller;
import com.encore.book.data.entity.BookInfo;
import com.encore.book.data.repository.BestSellerRepository;
import com.encore.book.data.repository.BookInfoRepository;
import com.encore.book.service.BookService;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookInfoRepository bookInfoRepository;

    @Autowired
    private BestSellerRepository bestSellerRepository;


    @Override
    public List<BookInfoDto> inquiry() {
       
        List<BookInfo> selected = bookInfoRepository.findAll();

        List<BookInfoDto> dtoList = new ArrayList<>();
        
        for (BookInfo bookInfo : selected) {
            BookInfoDto dto = new BookInfoDto();
            dto.setId(bookInfo.getId());
            dto.setAuthor(bookInfo.getAuthor());
            dto.setGenre(bookInfo.getGenre());

            dtoList.add(dto);
        }
        
        return dtoList;
    }


    @Override
    public List<BestSellerDto> seletBestSeller() {
        List<BestSeller> bestSellers = bestSellerRepository.findAll();

        List<BestSellerDto> bestSellerDto = new ArrayList<>();

        for (BestSeller bestSeller : bestSellers) {

            BestSellerDto dto = new BestSellerDto();
            dto.setId(bestSeller.getId());
            dto.setYear(bestSeller.getYear());

            bestSellerDto.add(dto);
        }

        return bestSellerDto;
    }

    @Override
    public BookInfoDto getBookInfo(String id) {
        
        BookInfo bookInfo = bookInfoRepository.findById(id).orElse(null);

        BookInfoDto bookInfoDto = new BookInfoDto();
        bookInfoDto.setId(bookInfo.getId());
        bookInfoDto.setAuthor(bookInfo.getAuthor());
        bookInfoDto.setGenre(bookInfo.getGenre());
        bookInfoDto.setDate(bookInfo.getDate());
        bookInfoDto.setIntro(bookInfo.getIntro());
        bookInfoDto.setName(bookInfo.getName());
        bookInfoDto.setPrice(bookInfo.getPrice());
        bookInfoDto.setPublisher(bookInfo.getPublisher());
        bookInfoDto.setStarpoint(bookInfo.getStarpoint());

        return bookInfoDto;
    }


    @Override
    public Page<BookDto> getBookInfoAndBookSellerAndPage(Pageable pageable, String year, String month) {
        System.out.println("year::: " + year + " month:::" + month);
       return bestSellerRepository.queryByBookInfoWithPage(pageable, year, month);
    }


    @Override
    public Page<BookInfoDto> getSearchBookListByName(Pageable pageable, String search) {
        return bookInfoRepository.queryBySearchBookListByName(pageable, search);
    }

    @Override
    public Page<BookInfoDto> getSearchBookListByAuthor(Pageable pageable, String search) {
        return bookInfoRepository.queryBySearchBookListByAuthor(pageable, search);
    }

    @Override
    public Page<BookInfoDto> getSearchBookListByPublisher(Pageable pageable, String search) {
        return bookInfoRepository.queryBySearchBookListByPublisher(pageable, search);
    }

    @Override
    public Page<BookInfoDto> getSearchBookListByAll(Pageable pageable, String search) {
        return bookInfoRepository.queryBySearchBookListByAll(pageable, search);
    }
}
