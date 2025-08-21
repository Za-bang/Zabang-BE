package com.example.zabang_be.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class PythonApiResponseDto {
    @JsonProperty("keywords")
    private String keywords;
}
