package com.spring.chatApp.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(AuthoritiesId.class)
@Table(name = "authorities",schema = "chat")
public class Authorities implements GrantedAuthority {
    @Id
    private UUID userId;
    @Id
    private String authority;

}
