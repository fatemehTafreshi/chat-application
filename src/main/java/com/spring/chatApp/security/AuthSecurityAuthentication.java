package com.spring.chatApp.security;

import com.spring.chatApp.configuration.AppJwtProperties;
import com.spring.chatApp.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity
public class AuthSecurityAuthentication {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private AppJwtProperties appJwtProperties;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(appJwtProperties.getKey()).build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtDecoder jwtDecoder) throws Exception {
        http.userDetailsService(myUserDetailsService);


        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/authenticate/**").permitAll()
                .requestMatchers("/admin/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers("/user/**").hasAnyAuthority("SCOPE_USER","SCOPE_ADMIN")
                .anyRequest().authenticated());



        http.formLogin(Customizer.withDefaults());

        http.oauth2ResourceServer((oauth2ResourceServer) ->
                oauth2ResourceServer
                        .jwt((jwt) ->
                                jwt
                                        .decoder(jwtDecoder)
                        )
        );


        http.csrf(csrf -> csrf.disable());
        http.headers(headers -> headers.frameOptions(a -> a.sameOrigin()));
        return http.build();
    }

}




