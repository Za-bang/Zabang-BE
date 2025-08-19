package com.example.zabang_be.service;

import com.example.zabang_be.dto.*;
import com.example.zabang_be.entity.OptionEntity;
import com.example.zabang_be.entity.RoomEntity;
import com.example.zabang_be.repository.RoomRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;

    // 단건 등록
    public RoomResponseDto create(RoomCreateRequestDto req) {
        RoomEntity room = new RoomEntity(
                null,
                req.name(),
                req.imagePath(),
                req.deposit(),
                req.rentPrice(),
                req.wide(),
                req.address(),
                req.area(),
                req.phoneNumber(),
                new ArrayList<>(), // keywords
                null               // option (초기 null)
        );

        // 단일 옵션 세팅
        if (req.options() != null) {
            OptionRequestDto o = req.options();
            OptionEntity op = new OptionEntity();
            op.setAirConditioner(o.airConditioner());
            op.setRefrigerator(o.refrigerator());
            op.setWashingMachine(o.washingMachine());
            op.setDryer(o.dryer());
            op.setPet(o.pet());
            op.setBed(o.bed());
            op.setMicrowave(o.microwave());
            op.setBalcony(o.balcony());
            op.setFireProtection(o.fireProtection());
            op.setGas(o.gas());
            op.setWifi(o.wifi());
            op.setCctv(o.cctv());
            op.setParkingLot(o.parkingLot());

            room.setOption(op); // ★ 양방향 연결(편의 메서드)
        }

        RoomEntity saved = roomRepository.save(room); // cascade로 option 함께 저장
        return toResponse(saved);
    }

//    // 더미 데이터 여러 개
    public List<RoomResponseDto> seedDummy() {
        return importFromDefaultJson();
    }

    // ==== 변환 ====
    private RoomResponseDto toResponse(RoomEntity e) {
        OptionResponseDto optionDto = null;
        OptionEntity o = e.getOption();
        if (o != null) {
            optionDto = new OptionResponseDto(
                    o.getOptionId(),
                    o.isAirConditioner(),
                    o.isRefrigerator(),
                    o.isWashingMachine(),
                    o.isDryer(),
                    o.isPet(),
                    o.isBed(),
                    o.isMicrowave(),
                    o.isBalcony(),
                    o.isFireProtection(),
                    o.isGas(),
                    o.isWifi(),
                    o.isCctv(),
                    o.isParkingLot()
            );
        }

        return new RoomResponseDto(
                e.getRoomId(),
                e.getName(),
                e.getImagePath(),
                e.getDeposit(),
                e.getRentPrice(),
                e.getWide(),
                e.getAddress(),
                e.getArea(),
                e.getPhoneNumber(),
                optionDto
        );
    }

    public List<RoomResponseDto> importFromJson(String classpathJson) {
        try {
            ClassPathResource resource = new ClassPathResource(classpathJson); // 예: "rooms.json"
            try (InputStream is = resource.getInputStream()) {
                List<RoomCreateRequestDto> list = objectMapper.readValue(
                        is, new TypeReference<List<RoomCreateRequestDto>>() {}
                );
                List<RoomResponseDto> result = new ArrayList<>();
                for (RoomCreateRequestDto dto : list) {
                    result.add(create(dto)); // 기존 create 로직 사용(옵션 포함, cascade 저장)
                }
                return result;
            }
        } catch (Exception e) {
            throw new RuntimeException("JSON 가져오기/파싱 실패: " + classpathJson, e);
        }
    }

    public List<RoomResponseDto> importFromDefaultJson() {
        return importFromJson("rooms.js");
    }
}