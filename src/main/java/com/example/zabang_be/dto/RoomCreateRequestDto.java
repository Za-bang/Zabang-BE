package com.example.zabang_be.dto;

import jakarta.validation.constraints.*;

public record RoomCreateRequestDto(
        @NotBlank String name,
        String imagePath,
        @NotNull @PositiveOrZero Long deposit,
        @NotBlank String rentPrice,
        @NotNull @Positive double wide,
        @NotBlank String address,
        String area,
        @NotBlank String phoneNumber,
        @NotNull double latitude,
        @NotNull double longitude,
        OptionRequestDto options  // 옵션 여러 건
) {}
