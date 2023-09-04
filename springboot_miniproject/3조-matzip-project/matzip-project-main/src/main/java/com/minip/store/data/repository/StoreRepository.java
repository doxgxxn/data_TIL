package com.minip.store.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.minip.store.data.entity.Store;

import java.util.ArrayList;
import java.util.List;

public interface StoreRepository extends JpaRepository<Store, String> {
    @Query("SELECT t FROM Store t WHERE t.name like %:every% or t.location like %:every% or t.type like %:every% or t.site like %:every%")
    List<Store> queryByEvery(String every);

    @Query("SELECT t FROM Store t WHERE t.name = :name")
    List<Store> queryByName(String name);

    @Override
    ArrayList<Store> findAll();
    // @Query("SELECT t FROM Stock t WHERE t.stockCode = :stockcode AND t.date>= :from_to AND t.date <= :end_to")
    // List<Store> queryByStoreCodeAndDateAndDate(String stockcode, String from_to, String end_to);
}
