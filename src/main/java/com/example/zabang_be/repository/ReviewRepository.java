package com.example.zabang_be.repository;

import com.example.zabang_be.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
    boolean existsByRoom_RoomIdAndAuthor(Long roomId, String author);
}