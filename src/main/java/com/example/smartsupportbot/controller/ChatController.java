package com.example.smartsupportbot.controller;

import com.example.smartsupportbot.dto.ChatRequest;
import com.example.smartsupportbot.model.User;
import com.example.smartsupportbot.repository.UserRepository;
import com.example.smartsupportbot.service.ChatService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final UserRepository userRepository;

    public ChatController(ChatService chatService, UserRepository userRepository) {
        this.chatService = chatService;
        this.userRepository = userRepository;
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestBody ChatRequest request, Authentication authentication){

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("user not found"));
        return chatService.askAI(request.getQuestion(),user);
    }
}
