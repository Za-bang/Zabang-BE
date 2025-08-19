package com.example.zabang_be.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewCreateRequestDto {

    @NotNull(message = "평점은 필수입니다.")
    @Min(value = 1, message = "평점은 최소 1점이어야 합니다.")
    @Max(value = 5, message = "평점은 최대 5점이어야 합니다.")
    private Integer rating;

    @NotBlank(message = "리뷰 내용을 입력해주세요.")
    @Size(max = 2000, message = "리뷰 내용은 2000자 이하로 작성해주세요.")
    private String content;

    private List<MultipartFile> images;
}