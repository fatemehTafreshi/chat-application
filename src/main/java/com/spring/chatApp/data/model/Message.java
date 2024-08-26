package com.spring.chatApp.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
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

    @Column(name = "text")
    @NotNull(message = "message must not be null!")
    private String text;

    @Column(name = "sentDate")
    @Past(message = "sent date must be in the past!")
    private LocalDateTime sentDate;

    @Column(name = "seenDate")
    @Past(message = "seen date must be in the past!")
    private LocalDateTime seenDate;

    private UUID senderId;

    private UUID recipientId;


}