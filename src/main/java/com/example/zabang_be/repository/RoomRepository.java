package com.example.zabang_be.repository;

import com.example.zabang_be.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    // 빌라, 원룸을 이름으로 검색할 수 있는 메서드 선언 (포함하는 값도 검색된다 SQL의 Like)
    List<RoomEntity> findRoomEntitiesByNameContaining(String name);
    // 키워드는 지정되어 있기 때문에 굳이 포함 값은 하지 않음
    List<RoomEntity> findRoomEntitiesByKeywords(List<String> keywords);
}
