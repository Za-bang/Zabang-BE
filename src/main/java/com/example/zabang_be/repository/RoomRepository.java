package com.example.zabang_be.repository;

import com.example.zabang_be.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    // 빌라, 원룸을 이름으로 검색할 수 있는 메서드 선언 (포함하는 값도 검색된다 SQL의 Like)
    List<RoomEntity> findRoomEntitiesByNameContaining(String name);
}
