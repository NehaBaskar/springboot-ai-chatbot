package com.example.smartsupportbot.controller;

import com.example.smartsupportbot.dto.AuthResponse;
import com.example.smartsupportbot.dto.LoginRequest;
import com.example.smartsupportbot.dto.RegisterRequest;
import com.example.smartsupportbot.model.User;
import com.example.smartsupportbot.security.JWTUtil;
import com.example.smartsupportbot.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JWTUtil jwtUtil;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegisterRequest request){
        authService.register(request);
        return "User Registered Successfully";
    }

    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody LoginRequest request){
        User user = authService.authenticate(request);
        String token = jwtUtil.generateToken(request.getEmail());
        return new AuthResponse(token);
    }
}
