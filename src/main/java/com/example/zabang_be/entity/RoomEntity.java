package com.example.zabang_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // roomID가 자동으로 증가할 수 있도록 AUTO_INTREMNET
    @Column(name = "roomid")    // 빌라, 원룸을 구분짓기 위한 고유 Id 생성
    private Long roomId;

    @Column(name = "name", nullable = false)    // 빌라, 원룸 이름
    private String name;

    @Column(name = "image", nullable = true)    // 이미지는 필수는 아님
    private String imagePath;    // 이미 경로 DB에 저장

    @Column(name = "deposit", nullable = false)   // 보증금
    private Long deposit;

    @Column(name = "rentprice", nullable = false)   // 사용자가 년세 얼마, 월세 얼마 이런 식으로 입력할 수 있게 유도
    private String rentPrice;

    @Column(name = "wide", nullable = false)  // 평수 m^2 단위니 소수점 허용
    private double wide;

    @Column(name = "address", nullable = false)   // 주소
    private String address;

    @Column(name = "area")  // {1, 2, 3, 4} 구역을 의미
    private String area;

    @Column(name = "phonenumber", nullable = false) // 집주인 전화번호
    private String phoneNumber;

    // 해당 방에 대한 키워드를 리스트로 따로 저장
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<KeywordEntity> keywords = new ArrayList<>();
    // 해당 방에 대한 옵션들을 리스트로 따로 저장
    @OneToOne(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    OptionEntity option;
}
