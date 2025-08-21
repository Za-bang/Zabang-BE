package com.example.zabang_be.controller;

import com.example.zabang_be.dto.KeywordSearchRequestDto;
import com.example.zabang_be.dto.RoomSearchRequestDto;
import com.example.zabang_be.dto.RoomSearchResponseDto;
import com.example.zabang_be.service.SearchRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class RoomSearchController {
    private final SearchRoomService searchRoomService;

    // spring bean 등록
    public RoomSearchController(SearchRoomService searchRoomService) {
        this.searchRoomService = searchRoomService;
    }

    @GetMapping("/rooms/search")
    public ResponseEntity<?> searchRoom(@RequestParam String name) {
        // 입력받은 dto에서 이름을 가져와 Room을 찾아서 List에 저장
        List<RoomSearchResponseDto> result = searchRoomService.searchRoomByName(name);

        // 만약 값이 존재하지 않는다면
        if(result.isEmpty()) {
            // 검색 값이 존재하지 않는다면 message 출력
            return ResponseEntity.ok(Map.of("message", String.format("\"%s\"이란 방을 찾을 수 없습니다.", name)));
        }
        return ResponseEntity.ok(result);   // 검색 성공 값 있음 (200 Ok)
    }

    @GetMapping("/rooms/search/keywords")
    public ResponseEntity<?> searchRoomsByKeywords(@RequestParam List<String> keywords) {
        // 입력받은 dto에서 키워드를 가져와 Room을 찾아서 List에 저장
        List<RoomSearchResponseDto> result = searchRoomService.searchRoomByKeyword(keywords);

        // 만약 값이 존재하지 않는다면
        if(result.isEmpty()) {
            // 검색 값이 존재하지 않는다면 message 출력
            return ResponseEntity.ok(Map.of("message", ("키워드에 해당하는 방을 찾을 수 없습니다.")));
        }
        return ResponseEntity.ok(result);   // 검색 성공 값 있음 (200 Ok)
    }
}