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

    @OneToOne(fetch = FetchType.LAZY) // room의 한개의 행과 option의 한개의 행이 1:1 매칭
    @JoinColumn(name = "roomid", nullable = false, unique = true)    // 가져오는 외래키 컬럼
    private RoomEntity room;

    @Column(name = "airconditioner") //에어컨
    private boolean airConditioner;

    @Column(name = "refrigerator") // 냉장고
    private boolean refrigerator;

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

    @Column(name="fireprotection") // 소방시설
    private boolean fireProtection;

    @Column(name="gas") //도시가스
    private boolean gas;

    @Column(name="wifi")  //와이파이
    private boolean wifi;

    @Column(name="cctv") // cctv
    private boolean cctv;

    @Column(name="parkinglot") //주차장
    private boolean parkingLot;
}
