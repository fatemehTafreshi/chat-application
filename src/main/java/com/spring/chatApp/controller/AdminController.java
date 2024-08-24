package com.spring.chatApp.controller;

import com.spring.chatApp.data.model.Authorities;
import com.spring.chatApp.data.model.User;
import com.spring.chatApp.dto.UserDto;
import com.spring.chatApp.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/admin/users")
    public List<UserDto> getAllUsers() {

        return adminService.getAllUsers();
    }

    @PostMapping("/admin/create-user")
    public ResponseEntity<String> createUser(@RequestBody @Valid User user) {

        return adminService.createUser(user);
    }

    @DeleteMapping("/admin/delete-user")
    public ResponseEntity<String> deleteUser(@RequestParam UUID id) {


        return adminService.deleteUser(id);
    }

    @GetMapping("/admin/authorities/user")
    public Authorities getAuthoritiesForOneUser(@RequestParam UUID id) {
        return adminService.getAuthoritiesForOneUser(id);
    }

    @GetMapping("/admin/get-loggedIn-user")
    public List<UserDto> getLoggedInUser(Authentication authentication) {
        String username = authentication.getName();
        return adminService.getLoggedInUser(username);
    }

    @PostMapping("/admin/authorities/user")
    public ResponseEntity<String> setAuthoritiesForOneUser(@RequestParam UUID id, @RequestBody @Valid List<String> authorities) {
        return adminService.setAuthoritiesForOneUser(id, authorities);
    }
}
