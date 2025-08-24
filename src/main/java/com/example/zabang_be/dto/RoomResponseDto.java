package com.example.zabang_be.dto;

public record RoomResponseDto(
        String roomId,
        String name,
        String imagePath,
        Long deposit,
        String rentPrice,
        double wide,
        String address,
        String area,
        String phoneNumber,
        double latitude,
        double longitude,
        OptionResponseDto options
) {}