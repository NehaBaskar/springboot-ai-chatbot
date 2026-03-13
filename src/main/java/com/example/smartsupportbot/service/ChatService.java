package com.example.smartsupportbot.service;

import com.example.smartsupportbot.model.ChatHistory;
import com.example.smartsupportbot.model.User;
import com.example.smartsupportbot.repository.ChatHistoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ChatHistoryRepository chatHistoryRepository;

    public ChatService(RestTemplate restTemplate, ChatHistoryRepository chatHistoryRepository) {
        this.restTemplate = restTemplate;
        this.chatHistoryRepository = chatHistoryRepository;
    }

    public String askAI(String question, User user){
        String url = "https://api.openai.com/v1/chat/completions";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4o-mini");

        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", question);
        messages.add(userMessage);
        body.put("messages",messages);

        HttpEntity<Map<String,Object>> request = new
                HttpEntity<>(body,headers);

       Map response = restTemplate.postForObject(url,request,Map.class);

       List choices = (List) response.get("choices");
       Map firstChoice = (Map) choices.get(0);
       Map message = (Map) firstChoice.get("message");

       String aiResponse = message.get("content").toString();

       ChatHistory chat = new ChatHistory();
       chat.setQuestion(question);
       chat.setAnswer(aiResponse);
       chat.setUser(user);
       chat.setTimestamp(LocalDateTime.now());

       chatHistoryRepository.save(chat);

       return aiResponse;
    }
}
