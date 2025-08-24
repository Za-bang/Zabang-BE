package com.example.zabang_be.repository;

import com.example.zabang_be.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    // 빌라, 원룸을 이름으로 검색할 수 있는 메서드 선언 (포함하는 값도 검색된다 SQL의 Like)
    List<RoomEntity> findRoomEntitiesByNameContaining(String name);
    List<RoomEntity> findRoomEntitiesByKeywordsContaining(List<String> keywords);
    boolean existsByNameAndAddress(String name, String address);

    // 위도 경도 일치하는 방 찾기
    Optional<RoomEntity> findByLatitudeAndLongitude(double latitude, double longitude);
}
