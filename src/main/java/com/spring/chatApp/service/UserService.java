package com.spring.chatApp.service;

import com.spring.chatApp.data.model.Authorities;
import com.spring.chatApp.data.model.User;
import com.spring.chatApp.data.repository.UserRepository;
import com.spring.chatApp.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    @Transactional
    public ResponseEntity<String> changePassword(String password, Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName());
        if (!passwordEncoder.matches(password, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return ResponseEntity.ok("Password changed Successfully!");
//        return new LoginResponse(jwtService.generateJWT(user.getUsername(), user.getAuthorities()));

        } else {
            return new ResponseEntity<>(
                    "Your entered password is same as before!",
                    HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public ResponseEntity<String> registerUser(User user) {

        if (userRepository.findByUsername(user.getUsername()) == null) {
            User createdUser = userRepository.save(User.builder()
                    .username(user.getUsername())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .enabled(true)
                    .build());

            user.setAuthorities(Collections.singletonList(new Authorities(user.getId(), "USER")));
            userRepository.save(createdUser);
            return ResponseEntity.ok("User registered successfully!");
        }
        return new ResponseEntity<>(
                "Your user already exists!",
                HttpStatus.BAD_REQUEST);

    }
}
