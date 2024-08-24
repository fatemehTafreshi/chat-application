package com.spring.chatApp.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MessageDto {

    private UUID id;
    private String text;

    private LocalDateTime sentDate;
    private LocalDateTime seenDate;

    private UUID senderId;
    private UUID recipientId;

}


