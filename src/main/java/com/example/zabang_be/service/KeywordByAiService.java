    package com.example.zabang_be.service;

    import com.example.zabang_be.dto.PythonApiResponseDto;
    import com.theokanning.openai.completion.chat.ChatCompletionRequest;
    import com.theokanning.openai.completion.chat.ChatMessage;
    import com.theokanning.openai.service.OpenAiService;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;

    import java.time.Duration;
    import java.util.Arrays;
    import java.util.List;

    @Service
    public class KeywordByAiService {

        private final OpenAiService openAiService;
        
        // AI 프롬포트
        private final String developerPrompt = """
                Your task is to extract exactly 1–3 short, relevant keywords from a given review of a one-room apartment near a university.\s
                - Focus on distinctive aspects such as lighting, noise, cleanliness, location, and amenities.
                - Prefix positive keywords with '+' and negative with '-' (e.g., +임대인친절, -임대인친절)
                - If the keyword is used in a **negative context**, simply add a minus sign (`-`) in front of the keyword. Do not change the word itself.
                - Output only the keywords, separated by commas.
                - Do not include extra words, sentences, or punctuation beyond the keywords.

                Keywords=[건물깨끗, 엘리베이터, 주차장, CCTV, 보안좋음, 분리형원룸, 냉난방좋음, 채광좋음, 환기잘됨, 방넓음, 인테리어좋음, 옵션많음, 사진그대로, 관리비저렴, 공과금저렴, 가성비좋음, 조용한환경, 층간소음없음, 벽간소음없음, 임대인친절, 문제대응빠름, 소통원활, 반려동물가능,입주편리,가격대비만족, 벌레없음]

                Example:
                Input: "햇빛이 잘 들어와서 아침에 상쾌해요. 하지만 옆집 소음이 심해서 밤에 잠을 못 잤어요. 근처에 편의점이 있어서 좋아요."
                Output: "+채광, -조용한환경, +편의시설"
            """;

        // 생성자를 통해 application.properties에 있는 API 키를 주입받아 OpenAiService를 초기화
        public KeywordByAiService(@Value("${openapi_key}") String apiKey) {
            // API 키와 타임아웃(30초)을 설정하여 OpenAiService 객체를 생성합니다.
            this.openAiService = new OpenAiService(apiKey, Duration.ofSeconds(30));
        }

        // OpenAI API를 직접 호출합니다.
        public PythonApiResponseDto getKeywordsFromAi(String reviewText) {
            try {
                // OpenAI API에 보낼 메시지 목록을 구성
                List<ChatMessage> messages = Arrays.asList(
                        new ChatMessage("system", "You are a helpful assistant."),
                        new ChatMessage("developer", developerPrompt),
                        new ChatMessage("user", reviewText)
                );

                // OpenAI에 보낼 요청 객체를 생성
                ChatCompletionRequest request = ChatCompletionRequest.builder()
                        .model("gpt-4o-mini")
                        .messages(messages)
                        .temperature(0.0)
                        .build();

                // OpenAI API를 호출하고, 응답에서 키워드 텍스트만 추출
                String keywords = openAiService.createChatCompletion(request)
                        .getChoices().get(0).getMessage().getContent().strip();

                // 결과를 DTO에 담아 반환
                return new PythonApiResponseDto(keywords);

            } catch (Exception e) {
                System.out.println("OpenAI API 호출 실패: " + e.getMessage());
                return null;
            }
        }
    }