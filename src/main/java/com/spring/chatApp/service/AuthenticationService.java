package com.spring.chatApp.service;

import com.spring.chatApp.data.model.User;
import com.spring.chatApp.data.repository.UserRepository;
import com.spring.chatApp.dto.LoginRequest;
import com.spring.chatApp.dto.LoginResponse;
import com.spring.chatApp.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public LoginResponse authenticate(LoginRequest request) {
        User user = userRepository.findByUsername(request.username());
        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new BadCredentialsException("Invalid password");
        return new LoginResponse(jwtService.generateJWT(user.getUsername(), user.getAuthorities()));

    }
}
