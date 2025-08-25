package com.example.zabang_be.repository;

import com.example.zabang_be.entity.KeywordEntity;
import com.example.zabang_be.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeywordRepository extends JpaRepository<KeywordEntity, Long> {
    boolean existsByRoomAndKeyword(RoomEntity room, String keyword);
}
