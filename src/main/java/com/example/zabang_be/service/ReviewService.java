package com.example.zabang_be.service;

import com.example.zabang_be.dto.*;
import com.example.zabang_be.entity.*;
import com.example.zabang_be.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Setter
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReviewDto create(Long roomId, Long userId, ReviewCreateRequestDto req) {

        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found: " + roomId));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + userId));

        String author = resolveAuthor(user);
        // 중복 리뷰 방지
        if (reviewRepository.existsByRoom_RoomIdAndAuthor(roomId, author)) {
            throw new IllegalStateException("Already reviewed this room");
        }

        // 세터 기반으로 엔티티 생성
        ReviewEntity review = new ReviewEntity();
        review.setRoom(room);
        review.setAuthor(author);
        review.setTexts(req.getContent());
        review.setGrade(req.getRating().doubleValue());

        // author: 유저 표시명(닉네임 있으면 닉네임, 없으면 이름)
        review.setAuthor(resolveAuthor(user));

        // content → texts 로 매핑
        review.setTexts(req.getContent());

        // rating(Integer) → grade(double) 변환
        review.setGrade(req.getRating().doubleValue());

        // 이미지 업로드 미구현 시: 우선 첫 파일명만 저장(임시)
        String imagePath = null;
        if (req.getImages() != null && !req.getImages().isEmpty()) {
            imagePath = req.getImages().get(0).getOriginalFilename(); // 실제 업로드 구현 전 임시
        }
        review.setImagePath(imagePath);

        // createdAt/date 는 엔티티 @PrePersist 에서 자동 세팅된다고 가정
        ReviewEntity saved = reviewRepository.save(review);

        return toDto(saved);
    }

    private String resolveAuthor(UserEntity user) {
        try {
            String nickname = user.getNickName();   // UserEntity에 nickname 필드 → getNickname()
            if (nickname != null && !nickname.isBlank()) return nickname;
        } catch (Exception ignore) {}

        try {
            String username = user.getUserName();   // UserEntity에 username 필드 → getUsername()
            if (username != null && !username.isBlank()) return username;
        } catch (Exception ignore) {}

        // 필드 명 맞추기
        return "user-" + user.getUserId();
    }

    private ReviewDto toDto(ReviewEntity e) {
        return ReviewDto.fromEntity(e);
    }
}