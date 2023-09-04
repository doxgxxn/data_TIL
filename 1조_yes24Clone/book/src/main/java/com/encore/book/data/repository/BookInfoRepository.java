package com.encore.book.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.encore.book.data.dto.BookInfoDto;
import com.encore.book.data.entity.BookInfo;

public interface BookInfoRepository extends JpaRepository<BookInfo, String>{

    @Query("SELECT new com.encore.book.data.dto.BookInfoDto(" +
           " t.id, t.name, t.author, t.publisher, t.date, t.price, t.genre, t.intro, t.starpoint)" +
           " FROM BookInfo t WHERE (t.name like %:search%)")
    Page<BookInfoDto> queryBySearchBookListByName(Pageable pageable, String search);
 
    @Query("SELECT new com.encore.book.data.dto.BookInfoDto(" +
           " t.id, t.name, t.author, t.publisher, t.date, t.price, t.genre, t.intro, t.starpoint)" +
           " FROM BookInfo t WHERE (t.author like %:search%)")
    Page<BookInfoDto> queryBySearchBookListByAuthor(Pageable pageable, String search);

    @Query("SELECT new com.encore.book.data.dto.BookInfoDto(" +
           " t.id, t.name, t.author, t.publisher, t.date, t.price, t.genre, t.intro, t.starpoint)" +
           " FROM BookInfo t WHERE (t.publisher like %:search%)")
    Page<BookInfoDto> queryBySearchBookListByPublisher(Pageable pageable, String search);

    @Query("SELECT new com.encore.book.data.dto.BookInfoDto(" +
           " t.id, t.name, t.author, t.publisher, t.date, t.price, t.genre, t.intro, t.starpoint)" +
           " FROM BookInfo t WHERE (t.name like %:search% OR t.author like %:search% OR t.intro like %:search%)")
    Page<BookInfoDto> queryBySearchBookListByAll(Pageable pageable, String search);
}