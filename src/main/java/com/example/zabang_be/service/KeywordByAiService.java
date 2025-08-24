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

        // Python 코드에 있던 프롬프트를 그대로 Java 코드로 가져옵니다.
        private final String developerPrompt = """
            Your task is to extract exactly 2–3 short, relevant keywords from a given review of a one-room apartment near a university.
            - Focus on distinctive aspects such as lighting, noise, cleanliness, location, and amenities.
            - Output only the keywords, separated by commas.
            - Do not include extra words, sentences, or punctuation beyond the keywords.
    
            Example:
            Input: "The room gets a lot of sunlight in the morning, but the traffic noise is loud at night. The convenience store is just across the street."
            Output: "sunlight, traffic noise, convenience store"
            """;

        // 생성자를 통해 application.properties에 있는 API 키를 주입받아 OpenAiService를 초기화합니다.
        public KeywordByAiService(@Value("${openapi_key}") String apiKey) {
            // API 키와 타임아웃(30초)을 설정하여 OpenAiService 객체를 생성합니다.
            this.openAiService = new OpenAiService(apiKey, Duration.ofSeconds(30));
        }

        // 이 메소드는 이제 Python 서버 대신 OpenAI API를 직접 호출합니다.
        public PythonApiResponseDto getKeywordsFromAi(String reviewText) {
            try {
                // 1. OpenAI API에 보낼 메시지 목록을 구성합니다. (Python 코드와 동일한 구조)
                List<ChatMessage> messages = Arrays.asList(
                        new ChatMessage("system", "You are a helpful assistant."),
                        new ChatMessage("developer", developerPrompt),
                        new ChatMessage("user", reviewText)
                );

                // 2. OpenAI에 보낼 요청 객체를 생성합니다.
                ChatCompletionRequest request = ChatCompletionRequest.builder()
                        .model("gpt-4o-mini") // 사용할 AI 모델
                        .messages(messages)        // 보낼 메시지
                        .temperature(0.0)          // 결과의 일관성을 위해 0으로 설정
                        .build();

                // 3. OpenAI API를 호출하고, 응답에서 키워드 텍스트만 추출합니다.
                String keywords = openAiService.createChatCompletion(request)
                        .getChoices().get(0).getMessage().getContent().strip();

                // 4. 결과를 DTO에 담아 반환합니다.
                return new PythonApiResponseDto(keywords);

            } catch (Exception e) {
                // 실제 서비스에서는 System.out.println 대신 로깅 프레임워크 사용을 권장합니다.
                System.out.println("OpenAI API 호출 실패: " + e.getMessage());
                return null;
            }
        }
    }