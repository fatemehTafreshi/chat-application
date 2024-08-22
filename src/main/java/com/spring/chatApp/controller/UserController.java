package com.spring.chatApp.controller;

import com.spring.chatApp.data.model.User;
import com.spring.chatApp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/user/change-password")
    public ResponseEntity<String> changePassword(@RequestBody String password, Authentication authentication) {

        return userService.changePassword(password, authentication);
    }

    @PostMapping("/user/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid User user) {

        return userService.registerUser(user);

    }


}
