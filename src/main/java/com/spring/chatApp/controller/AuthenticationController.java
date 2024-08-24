package com.spring.chatApp.controller;

import com.spring.chatApp.dto.LoginRequest;
import com.spring.chatApp.dto.LoginResponse;
import com.spring.chatApp.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/authenticate")
    public LoginResponse authenticate(@RequestBody LoginRequest request) {
        return authenticationService.authenticate(request);

    }


}


