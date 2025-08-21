package com.example.zabang_be.service;

import com.example.zabang_be.dto.PythonApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class KeywordByAiService {
    // 외부 API를 호출할 때 사용하는 RestTemplate
    private final RestTemplate restTemplate = new RestTemplate();;

    private final String pythonApi = "http://127.0.0.1:5000/extract-keywords";

    // reviewText를 AI 서버로 전송 후 키워드를 추출한 DTO를 반환
    public PythonApiResponseDto getKeywordsFromAi(String reviewText) {
        try {
            // Http 요청 헤더
            HttpHeaders headers = new HttpHeaders();
            // 보내는 파일 형식 JSON
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Http 요청 본문 생성
            HttpEntity<?> request = new HttpEntity<>(
                    Collections.singletonMap("review", reviewText),
                    headers
            );

            // AI 서버로 전송
            return restTemplate.postForObject(pythonApi, request, PythonApiResponseDto.class);
        } catch (Exception e) {
            System.out.println("API 호출 실패 : " + e.getMessage());
            return null;
        }
    }
}
