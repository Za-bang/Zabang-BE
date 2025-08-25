package com.example.zabang_be.service;

import com.example.zabang_be.dto.*;
import com.example.zabang_be.entity.*;
import com.example.zabang_be.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;


@Service
@RequiredArgsConstructor
@Setter
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RoomRepository roomRepository;
    private final KeywordRepository keywordRepository;
    private final KeywordByAiService keyword;
    private final KeywordByAiService keywordByAiService;


    @Transactional
    public ReviewDto create(Long roomId, ReviewCreateRequestDto req) {
        RoomEntity room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found: " + roomId));

        String author = "익명";

        ReviewEntity review = new ReviewEntity();
        review.setRoom(room);
        review.setAuthor(author);
        review.setTexts(req.getContent());
        review.setGrade(req.getRating().doubleValue());
        review.setDate(LocalDateTime.now()); // @PrePersist로 자동이면 제거하세요.

        // 이미지 업로드 미구현: 우선 첫 파일명만 저장(임시)
        if (req.getImages() != null && !req.getImages().isEmpty()) {
            review.setImagePath(req.getImages().get(0).getOriginalFilename());
        }

        // AI 키워드 추출 & 저장
        try {
            PythonApiResponseDto api = keywordByAiService.getKeywordsFromAi(req.getContent());
            if (api != null && api.getKeywords() != null) {
                String keywords = api.getKeywords(); // "조용함, 채광, 역세권"
                review.setKeyword(keywords);         // review 테이블에 문자열로 저장
                saveKeyword(room, keywords);        // keywords 테이블에 분리 저장
            }
        } catch (Exception e) {
            System.out.println("키워드 추출 실패: " + e.getMessage());
        }

        ReviewEntity saved = reviewRepository.save(review);
        return ReviewDto.fromEntity(saved); // ReviewDto에서 roomId를 String으로 채우는 로직 포함
    }

    private void saveKeyword(RoomEntity room, String keyword) {
        // 공백과 쉼표를 기준으로 분리
        String[] keywords = keyword.split(",\\s*");

        // keywords 테이블에 저장
        Arrays.stream(keywords).filter(key-> !key.isEmpty())
                .forEach(key-> {
                    KeywordEntity keywordEntity = new KeywordEntity();
                    keywordEntity.setRoom(room);
                    keywordEntity.setKeyword(key.trim());   // 앞뒤 공백 제거
                    keywordRepository.save(keywordEntity);
                });
    }
}