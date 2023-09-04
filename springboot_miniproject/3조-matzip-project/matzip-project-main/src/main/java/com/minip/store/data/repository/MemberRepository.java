package com.minip.store.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.minip.store.data.entity.Member;

import java.util.ArrayList;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, String> {
    
    @Query("SELECT t FROM Member t WHERE t.name = :name AND t.password = :password")
    List<Member> findByName(String name, String password);

    @Override
    ArrayList<Member> findAll();

    @Query("SELECT t FROM Member t WHERE t.name = :name")
    List<Member> existsByName(String name);

    @Query("SELECT t FROM Member t WHERE t.name IN :names")
    List<Member> deleteByNames(@Param("names") String[] names);
}
