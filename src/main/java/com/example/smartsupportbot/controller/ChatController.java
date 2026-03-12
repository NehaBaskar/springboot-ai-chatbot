package com.example.smartsupportbot.controller;

import com.example.smartsupportbot.dto.ChatRequest;
import com.example.smartsupportbot.service.ChatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestBody ChatRequest request){
        return chatService.askAI(request.getQuestion());
    }
}
