package com.example.zabang_be.service;

import com.example.zabang_be.dto.RoomSearchRequestDto;
import com.example.zabang_be.dto.RoomSearchResponseDto;
import com.example.zabang_be.entity.KeywordEntity;
import com.example.zabang_be.entity.RoomEntity;
import com.example.zabang_be.repository.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchRoomService {
    private RoomRepository roomRepository;

    // spring bean 등록
    public SearchRoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    // name에 해당하는 RoomSearchResponseDto return
    public List<RoomSearchResponseDto> searchRoomByName(String name) {
        // name 문자열이 포함된 값을 모두 검색
        List<RoomEntity> findRooms = roomRepository.findRoomEntitiesByNameContaining(name);

        return findRooms.stream()
                .map(RoomSearchResponseDto::fromEntity) // Entity -> Dto
                .collect(Collectors.toList());  // 결과를 List로 반환
    }

    public List<RoomSearchResponseDto> searchRoomByKeyword(List<String> keywords) {
        List<RoomEntity> room = roomRepository.findAll();

        return room.stream()
                .filter(rooms -> rooms.getKeywords().stream()
                        .map(KeywordEntity::getKeyword) // KeywordEntity를 String으로
                        .collect(Collectors.toSet())    // Set으로 변환
                        .containsAll(keywords)) // 검색하려는 모든 키워드를 포함하는지 확인
                .map(RoomSearchResponseDto::fromEntity) // Entity -> Dto 변환
                .collect(Collectors.toList());
    }
}