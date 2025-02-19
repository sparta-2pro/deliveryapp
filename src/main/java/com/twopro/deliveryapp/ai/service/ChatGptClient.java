package com.twopro.deliveryapp.ai.service;

import com.twopro.deliveryapp.ai.exception.InvalidChatGptResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatGptClient {

    @Value("${ai.openai.api.key}")
    private final String apiKey;

    @Value("${ai.openai.api.url}")
    private final String apiUrl;

    private final RestTemplate restTemplate;

    public String generateDescription(String question) {
        Map response = sendQuestion(question);
        return getAnswer(response);
    }

    private Map sendQuestion(String question) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> payload = new HashMap<>();
        payload.put("model", "gpt-3.5-turbo");
        payload.put("messages", List.of(Map.of("role", "user", "content", question)));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(payload, headers);
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(apiUrl, requestEntity, Map.class);

        return responseEntity.getBody();
    }

    private String getAnswer(Map<String, Object> response) {
        isResponseValid(response);

        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
        isChoiceEmpty(choices);

        Map<String, Object> message = choices.get(0);
        isMessageValid(message);

        return message.get("content").toString();
    }

    private static void isResponseValid(Map<String, Object> response) {
        if (response == null || !response.containsKey("choices")) {
            throw new InvalidChatGptResponseException("ChatGPT 응답 값이 올바르지 않습니다!");
        }
    }

    private static void isChoiceEmpty(List<Map<String, Object>> choices) {
        if (choices.isEmpty()) {
            throw new InvalidChatGptResponseException("ChatGPT 응답 값의 내용이 비어있습니다!");
        }
    }

    private static void isMessageValid(Map<String, Object> message) {
        if (message == null || !message.containsKey("content")) {
            throw new InvalidChatGptResponseException("ChatGPT 답변 내용이 비어있습니다!");
        }
    }
}
