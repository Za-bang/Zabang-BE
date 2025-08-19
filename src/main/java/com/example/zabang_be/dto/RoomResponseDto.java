package com.example.zabang_be.dto;

public record RoomResponseDto(
    Long roomId,
    String name,
    String imagePath,
    Long deposit,
    String rentPrice,
    double wide,
    String address,
    String area,
    String phoneNumber,
    OptionResponseDto options
) {}