package com.example.zabang_be.repository;

import com.example.zabang_be.entity.ReviewEntity;
import com.example.zabang_be.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    boolean existsByRoom_RoomIdAndAuthor(Long roomId, String author);
    boolean existsByRoom_RoomIdAndKeyword(Long roomId, String keyword);
    long countByRoomAndAuthorStartingWith(RoomEntity room, String texts);
}