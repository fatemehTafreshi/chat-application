package com.spring.chatApp.dto;

import com.spring.chatApp.data.model.Authorities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserDto {

    private UUID id;

    private String username;

    private boolean enabled;

    private String authority;

    public void setAuthorities(List<String> authorities) {
        this.authority = String.join(",", authorities);

    }

}
