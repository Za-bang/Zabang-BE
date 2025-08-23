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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final ObjectMapper objectMapper;

    // 단건 등록
    public RoomResponseDto createIfNotExists(RoomCreateRequestDto req) {

        if (roomRepository.existsByNameAndAddress(req.name(), req.address())) {
            // 스킵: 이미 있으므로 null 반환하거나, 스킵 표식 만들기
            return null;
        }
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
                req.latitude(),
                req.longitude(),
                new ArrayList<>(), // keywords
                null,        // option (초기 null)
                null         //reviews
        );

        // 단일 옵션 세팅
        if (req.options() != null) {
            OptionRequestDto o = req.options();
            OptionEntity op = new OptionEntity();
            op.setAircondtioner(o.airConditioner());
            op.setRefridgerator(o.refrigerator());
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
                    o.isAircondtioner(),
                    o.isRefridgerator(),
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
                e.getLatitude(),
                e.getLongitude(),
                optionDto
        );
    }

    //전체롤백방지--> 한 건에서 예외나오면 다음으로 넘어감
    public List<RoomResponseDto> importFromJson(String classpathJson) {
        try (InputStream is = new ClassPathResource(classpathJson).getInputStream()) {
            List<RoomCreateRequestDto> list =
                    objectMapper.readValue(is, new TypeReference<>() {
                    });
            List<RoomResponseDto> result = new ArrayList<>();

            for (RoomCreateRequestDto dto : list) {
                try {
                    RoomResponseDto saved = createIfNotExists(dto);
                    if (saved != null) result.add(saved);
                } catch (Exception ex) {
                    // 한 건 실패해도 계속 진행
                    System.err.println("[IMPORT SKIP] " + dto.name() + " - " + ex.getMessage());
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("JSON 가져오기/파싱 실패: " + classpathJson, e);
        }
    }

    /** 기본 파일명으로 호출하는 편의 메서드 */
    public List<RoomResponseDto> importFromDefaultJson() {
        return importFromJson("rooms.json");
    }


    public Optional<RoomResponseDto> getByCoordsExact(double lat, double lng) {
        return roomRepository.findByLatitudeAndLongitude(lat, lng)
                .map(this::toResponse); // toResponse는 기존 변환 메서드 사용
    }
}