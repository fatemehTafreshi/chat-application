package com.spring.chatApp;

import com.spring.chatApp.configuration.AppJwtProperties;
import com.spring.chatApp.configuration.Authority;
import com.spring.chatApp.data.model.Authorities;
import com.spring.chatApp.data.model.User;
import com.spring.chatApp.data.repository.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(AppJwtProperties.class)
public class ChatAppApplication {


    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(ChatAppApplication.class, args);
        if (args.length > 0 && args[0].equalsIgnoreCase("--add-user")) {

            UserRepository userRepository = ctx.getBean(UserRepository.class);
            PasswordEncoder passwordEncoder = ctx.getBean(PasswordEncoder.class);

            if (userRepository.findByUsername("user") == null) {
                User user = userRepository.save(User.builder()
                        .username("user")
                        .password(passwordEncoder.encode("pass"))
                        .enabled(true)
                        .build());

                Authorities user1 = new Authorities(user.getId(), "USER");
                Authorities user2 = new Authorities(user.getId(), "ADMIN");
                List<Authorities> authorities = new ArrayList<>();
                authorities.add(user1);
                authorities.add(user2);
                user.setAuthorities(authorities);

                userRepository.save(user);

            }
        }

    }
}
