package com.springboot.mini.data.entity;
// 엔티티는 데이터베이스의 테이블에 대응하는 클래스
// DB에 쓰일 테이블과 칼럼을 정의

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity // 해당 클래스가 엔티티임을 명시
@Table(name ="webtoon")
@AllArgsConstructor // 모든 필드를 매개변수로 갖는 생성자 자동 생성
@NoArgsConstructor // 매개변수가 없는 생성자 자동 생성
@ToString // toString() 메서드를 생성하는 어노테이션
@Getter
public class Webtoon {
    // 웹툰 고유 ID 값
    @Id// 이 어노테이션이 선언된 필드는 테이블의 기본값 역할을 수행.(필수 어노테이션)
    @Column
    private Long numb;

    @Column
    private Integer webtoonId;

    // 제목
    @Column
    private String title;
    
    // 글 작가
    @Column
    private String writer;
    
    // 그림 작가
    @Column
    private String painter;

    // 관람가
    @Column
    private String age;

    // 관심도
    @Column
    private Integer favorite;

    // 별점
    @Column
    private Float starScore;

   // 포스터 이미지
    @Column
    private String thumbnailUrl;

    // 연재 요일
    @Column
    private String publishDay;
    
    // 해시태그
    @Column
    private String hashTag;

    public Webtoon toEntity(){
        return new Webtoon(numb, webtoonId, title, writer, painter, age, favorite, starScore, thumbnailUrl, publishDay, hashTag);
    }
}