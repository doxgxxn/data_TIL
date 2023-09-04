package com.encore.book.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.encore.book.data.dto.BookDto;
import com.encore.book.data.entity.BestSeller;



public interface BestSellerRepository extends JpaRepository<BestSeller, String>{

    @Query("SELECT new com.encore.book.data.dto.BookDto(" +
            " b.id, c.name, c.author, c.publisher, c.date, c.price, c.genre, c.intro, c.starpoint, b.year, b.month, b.rank, CAST(c.price AS double) * 0.9, CAST(c.price AS double) * 0.05)" +
            " FROM BestSeller b JOIN BookInfo c" +
            " ON b.id = c.id")
    List<BookDto> queryByBookInfos();

    @Query("SELECT new com.encore.book.data.dto.BookDto(" +
            " b.id, c.name, c.author, c.publisher, c.date, c.price, c.genre, c.intro, c.starpoint, b.year, b.month, b.rank, CAST(c.price AS double) * 0.9, CAST(c.price AS double) * 0.05)" +
            " FROM BestSeller b JOIN BookInfo c" +
            " ON b.id = c.id" + 
            " WHERE b.year = :year" + 
            "   AND b.month = :month")
    // @Query("SELECT new com.encore.book.data.dto.BookDto(b.id, b.bookInfo.genre, b.bookInfo.author) FROM BestSeller b JOIN b.bookInfo c where b.year = :year and b.month = :month")
    Page<BookDto> queryByBookInfoWithPage(Pageable pageable, String year, String month);
}

