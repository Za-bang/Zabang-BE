package com.example.zabang_be.dto;

import com.example.zabang_be.entity.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewDto {
    private String author;
    private String texts;
    private double grade;
    private LocalDateTime date;
    private String imagePath;

    public static ReviewDto fromEntity(ReviewEntity review) {
        return new ReviewDto(
                review.getAuthor(),
                review.getTexts(),
                review.getGrade(),
                review.getDate(),
                review.getImagePath()
        );
    }
}
