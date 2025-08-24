package com.example.zabang_be.dto;

import com.example.zabang_be.entity.ReviewEntity;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    private String roomId;        // 프론트 요구: string
    private String author;
    private String texts;
    private double grade;
    private LocalDateTime date;
    private String imagePath;

    public static ReviewDto fromEntity(ReviewEntity review) {
        // review.getRoom()가 null일 경우 대비 (상황에 따라 예외로 바꿔도 됨)
        String roomIdStr = null;
        if (review.getRoom() != null && review.getRoom().getRoomId() != null) {
            roomIdStr = String.valueOf(review.getRoom().getRoomId());
        }

        return ReviewDto.builder()
                .roomId(roomIdStr)
                .author(review.getAuthor())
                .texts(review.getTexts())
                .grade(review.getGrade())
                .date(review.getDate())
                .imagePath(review.getImagePath())
                .build();
    }
}