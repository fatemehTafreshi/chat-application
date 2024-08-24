package com.spring.chatApp.data.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.spring.chatApp.configuration.JacksonLocalDateTimeDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "message",schema = "chat")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID messageId;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "sentDate")
    @JsonDeserialize(using = JacksonLocalDateTimeDeserializer.class)
    private LocalDateTime sentDate;

    @Column(name = "seenDate")
    @JsonDeserialize(using = JacksonLocalDateTimeDeserializer.class)
    private LocalDateTime seenDate;

    private UUID senderId;

    private UUID recipientId;


}