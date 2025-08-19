package com.example.zabang_be.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "options")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // optionId가 자동으로 증가 (AUTO_INCREMENT)
    @Column(name = "optionid")
    private Long optionId;

    // roomId로 option을 조회하기 위해 외래키로 가져온다
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roomid")    // 가져오는 외래키 컬럼
    private RoomEntity room;

    @Column(name = "airconditioner")   // 에어컨
    private boolean aircondtioner;

    @Column(name = "refridgerator") // 냉장고
    private boolean refridgerator;

    @Column(name = "washingmachine")    // 세탁기
    private boolean washingMachine;

    @Column(name = "dryer") // 건조기
    private boolean dryer;

    @Column(name = "pet")   // 애완동물 가능 유무
    private boolean pet;

    @Column(name = "bed")   // 침대
    private boolean bed;

    @Column(name = "microwave") // 전자레인지
    private boolean microwave;

    @Column(name = "balcony")   // 발코니
    private boolean balcony;

    @Column(name = "fireprotection")
    private boolean fireProtection;

    @Column(name = "gas")
    private boolean gas;

    @Column(name = "wifi")
    private boolean wifi;

    @Column(name = "cctv")
    private boolean cctv;

    @Column(name = "parkinglot")
    private boolean parkingLot;
}
