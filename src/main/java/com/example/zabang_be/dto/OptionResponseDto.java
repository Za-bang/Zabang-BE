package com.example.zabang_be.dto;

public record OptionResponseDto(
        Long optionId,
        boolean airConditioner,
        boolean refrigerator,
        boolean washingMachine,
        boolean dryer,
        boolean pet,
        boolean bed,
        boolean microwave,
        boolean balcony,
        boolean fireProtection,
        boolean gas,
        boolean wifi,
        boolean cctv,
        boolean parkingLot
) {}