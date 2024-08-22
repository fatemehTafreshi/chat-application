package com.spring.chatApp.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class MessageDto {

    private UUID id;
    private String text;

    private LocalDateTime sentDate;
    private LocalDateTime seenDate;

    private UUID senderId;
    private UUID recipientId;

}


