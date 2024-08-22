package com.spring.chatApp.data.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
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
@Table(name = "message", schema = "chat")
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
    private LocalDateTime seenDate;

//    @Column(columnDefinition = "VARCHAR(255)")
    private UUID senderId;

//    @Column(columnDefinition = "VARCHAR(255)")
    private UUID recipientId;


}