package com.spring.chatApp.repository;

import com.spring.chatApp.data.model.Message;
import com.spring.chatApp.data.repository.MessageRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop",

}, showSql = false)

public class MessageRepositoryTest {

    private Message message;
    @Autowired
    private MessageRepository messageRepository;


    @BeforeEach
    public void setUp() {
        Message message1 = new Message(null,
                "bbbbbbb",
                LocalDateTime.of(2024, 8, 22, 11, 35, 59),
                null,
                UUID.fromString("da6cc511-51b7-43e4-8cf0-e35bfc3b2d22"), UUID.fromString("3ae01670-78ee-46f0-bba0-8a86081220bc")
        );


        message = messageRepository.save(message1);

    }


    @Test
    void testFindMessage() {

        UUID messageId = message.getMessageId();


        Message foundMessage = messageRepository.findByMessageId(messageId);

        Assertions.assertEquals(message.getText(), foundMessage.getText(), "The text should match");

    }
}


