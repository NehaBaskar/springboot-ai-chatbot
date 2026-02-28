package com.example.smartsupportbot.service;

import com.example.smartsupportbot.dto.LoginRequest;
import com.example.smartsupportbot.dto.RegisterRequest;
import com.example.smartsupportbot.model.User;
import com.example.smartsupportbot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request){
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
    }

    public User authenticate(LoginRequest request){
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
            throw new RuntimeException("Invalid Credentials!!");
        }
        return user;
    }
}
