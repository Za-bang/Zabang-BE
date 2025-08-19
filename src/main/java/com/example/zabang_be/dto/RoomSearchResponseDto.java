package com.example.zabang_be.dto;

import com.example.zabang_be.entity.KeywordEntity;
import com.example.zabang_be.entity.OptionEntity;
import com.example.zabang_be.entity.ReviewEntity;
import com.example.zabang_be.entity.RoomEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class RoomSearchResponseDto {
    private String name;
    private String phoneNumber;
    private String address;
    private String imagePath;
    private Long deposit;
    private String rentPrice;
    private double wide;
    private String area;
    private List<String> keyword;
    private List<String> option;
    private List<ReviewDto> review;

    public static RoomSearchResponseDto fromEntity(RoomEntity room) {
        List<String> keywordList = room.getKeywords()
                .stream()
                .map(KeywordEntity::getKeyword)
                .collect(Collectors.toList());

        List<String> optionList = new ArrayList<>();
        OptionEntity optionEntity = room.getOption();
        
        // Entity에 값이 존재할때만 실행
        if(optionEntity != null) {
            // 해당 값이 존재한다면 List에 저장한다
            if (optionEntity.isAirConditioner()) optionList.add("에어컨");
            if (optionEntity.isRefrigerator()) optionList.add("냉장고");
            if (optionEntity.isWashingMachine()) optionList.add("세탁기");
            if (optionEntity.isDryer()) optionList.add("건조기");
            if (optionEntity.isBed()) optionList.add("침대");
            if (optionEntity.isPet()) optionList.add("애완동물 가능");
            if (optionEntity.isMicrowave()) optionList.add("전자레인지");
            if (optionEntity.isBalcony()) optionList.add("발코니");
            if (optionEntity.isFireProtection()) optionList.add("소방시설");
            if (optionEntity.isGas()) optionList.add("도시가스");
            if (optionEntity.isWifi()) optionList.add("와이파이");
            if (optionEntity.isCctv()) optionList.add("CCTV");
            if (optionEntity.isParkingLot()) optionList.add("주차장");
        }

        // 리뷰 리스트로 변환
        List<ReviewDto> reviewList = room.getReviews()
                .stream()
                .map(ReviewDto::fromEntity)
                .collect(Collectors.toList());

        // Dto를 만들어 반환
        return new RoomSearchResponseDto(
                room.getName(),
                room.getPhoneNumber(),
                room.getAddress(),
                room.getImagePath(),
                room.getDeposit(),
                room.getRentPrice(),
                room.getWide(),
                room.getArea(),
                keywordList,
                optionList,
                reviewList
        );
    }
}
