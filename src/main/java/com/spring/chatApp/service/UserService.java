package com.spring.chatApp.service;

import com.spring.chatApp.data.model.Authorities;
import com.spring.chatApp.data.model.User;
import com.spring.chatApp.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;


    @Transactional
    public ResponseEntity<String> changePassword(String password, String username) {

        User user = userRepository.findByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
            return ResponseEntity.ok("Password changed Successfully!");
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
