package com.example.zabang_be.controller;

import com.example.zabang_be.dto.RoomCreateRequestDto;
import com.example.zabang_be.dto.RoomResponseDto;
import com.example.zabang_be.dto.RoomSearchRequestDto;
import com.example.zabang_be.dto.RoomSearchResponseDto;
import com.example.zabang_be.service.RoomService;
import com.example.zabang_be.service.SearchRoomService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RoomController {
    private final SearchRoomService searchRoomService;
    private final RoomService roomService;


    // spring bean 등록
    public RoomController(SearchRoomService searchRoomService, RoomService roomService) {
        this.searchRoomService = searchRoomService;
        this.roomService = roomService;
    }

    @PostMapping("/rooms/search")
    public ResponseEntity<?> searchRoom(@RequestBody RoomSearchRequestDto dto) {
        // 입력받은 dto에서 이름을 가져와 Room을 찾아서 List에 저장
        List<RoomSearchResponseDto> result = searchRoomService.searchRoomByName(dto.getName());

        // 만약 값이 존재하지 않는다면
        if(result.isEmpty()) {
            // 검색 값이 존재하지 않는다면 message 출력
            return ResponseEntity.ok(Map.of("message", String.format("\"%s\"이란 방을 찾을 수 없습니다.", dto.getName())));
        }
        return ResponseEntity.ok(result);   // 검색 성공 값 있음 (200 Ok)
    }

    // 등록
    @PostMapping( "/rooms")
    public ResponseEntity<?> create(@RequestBody RoomCreateRequestDto dto) {
        try {
            // 검증 과정
            if (dto == null || dto.name() == null || dto.name().isBlank()) {
                return ResponseEntity.ok(Map.of("message", "name 필수 "));
            }
            if (dto.deposit() == null || dto.deposit() < 0) {
                return ResponseEntity.ok(Map.of("message", "deposit 필수 "));
            }
            if (dto.rentPrice() == null || dto.rentPrice().isBlank()) {
                return ResponseEntity.ok(Map.of("message", "rentPrice 필수 "));
            }
            if (dto.address() == null || dto.address().isBlank()) {
                return ResponseEntity.ok(Map.of("message", "address 필수"));
            }
            if (dto.phoneNumber() == null || dto.phoneNumber().isBlank()) {
                return ResponseEntity.ok(Map.of("message", "phonenumber 필수 "));
            }
            // option은 null이어도 허용

            RoomResponseDto saved = roomService.create(dto);
            return ResponseEntity.ok(saved);

        } catch (DataIntegrityViolationException e) {
            // FK/UNIQUE/NOT NULL 등 제약 위반
            String detail = e.getMostSpecificCause() != null ? e.getMostSpecificCause().getMessage() : e.getMessage();
            return ResponseEntity.ok(Map.of(
                    "message", "매물 저장 중 제약 조건 위반이 발생했습니다.",
                    "detail", detail
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.ok(Map.of(
                    "message", "입력값이 유효하지 않습니다.",
                    "detail", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                    "message", "매물 등록에 실패했습니다.",
                    "detail", e.getMessage()
            ));
        }
    }
    @PostMapping("/rooms/seed")
    public ResponseEntity<?> seedDummy() {
        try {
            List<RoomResponseDto> result = roomService.seedDummy();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                    "message", "더미데이터 등록 실패",
                    "detail", e.getMessage()
            ));
        }
    }
}