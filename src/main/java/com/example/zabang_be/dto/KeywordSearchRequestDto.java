package com.example.zabang_be.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class KeywordSearchRequestDto {
    private List<String> keywords;
}
