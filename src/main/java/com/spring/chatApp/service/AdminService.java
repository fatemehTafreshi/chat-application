package com.spring.chatApp.service;

import com.spring.chatApp.data.RowMapper.UserRowMapper;
import com.spring.chatApp.data.model.Authorities;
import com.spring.chatApp.data.model.User;
import com.spring.chatApp.data.repository.AdminRepository;
import com.spring.chatApp.data.repository.UserRepository;
import com.spring.chatApp.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class AdminService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    public List<UserDto> getAllUsers() {

        return jdbcTemplate.query("SELECT u.username,u.id,u.enabled,GROUP_CONCAT(a.authority SEPARATOR ' , ') AS authorities FROM chat.user u,chat.authorities a WHERE u.id = a.user_id group by user_id", new UserRowMapper());

    }

    public ResponseEntity<String> createUser(User user) {

        if (userRepository.findByUsername(user.getUsername()) == null) {
            User createdUser = userRepository.save(User.builder().username(user.getUsername()).password(passwordEncoder.encode(user.getPassword())).enabled(user.isEnabled()).build());

            Authorities user1 = new Authorities(createdUser.getId(), "USER");
            List<Authorities> authorities = new ArrayList<>();
            authorities.add(user1);
            createdUser.setAuthorities(authorities);

            userRepository.save(createdUser);
            return ResponseEntity.ok("Successfully created user.");
        }
        return new ResponseEntity<>("The user have been created before!", HttpStatus.BAD_REQUEST);
    }

    public List<UserDto> getLoggedInUser(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName());
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.isEnabled(), null);
        userDto.setAuthorities(user.getAuthorities().stream().map(Authorities::getAuthority).toList());
        return Collections.singletonList(userDto);
    }

    @Transactional
    public ResponseEntity<String> deleteUser(UUID id) {

        adminRepository.deleteById(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    @Transactional
    public Authorities getAuthoritiesForOneUser(UUID id) {
        return adminRepository.findAuthoritiesByUserId(id);
    }

    @Transactional
    public ResponseEntity<String> setAuthoritiesForOneUser(UUID id, Authorities authorities) {

        authorities.setUserId(id);
        adminRepository.save(authorities);
        return ResponseEntity.ok("Authority added successfully!");

//        authorities.setUserId(id);
//        List<Authorities> list = user.getAuthorities();
//        list.add(authorities);
//        user.setAuthorities(list);
//        adminRepository.save(authorities);
    }

    @Transactional
    public ResponseEntity<String> setAuthoritiesForOneUser(UUID id, List<String> types) {

        for (String type : types) {
            Authorities auth = new Authorities(id, type);
            adminRepository.save(auth);
        }
        return ResponseEntity.ok("Authority added successfully!");
    }
}