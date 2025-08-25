package com.example.zabang_be.controller;

import com.example.zabang_be.dto.*;
import com.example.zabang_be.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping(
            value = "/{roomId}/reviews",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable Long roomId,
            @Valid @ModelAttribute ReviewCreateRequestDto request
    ) {
        ReviewDto result = reviewService.create(roomId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}