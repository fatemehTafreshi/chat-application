package com.spring.chatApp.repository;

import com.spring.chatApp.data.model.Message;
import com.spring.chatApp.data.repository.MessageRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.UUID;

@DataJpaTest
public class MessageRepositoryTest {

    Message message;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private TestEntityManager entityManager;

    @BeforeEach
    public void setUp() {
        message = new Message(UUID.fromString("7e7c2f7c-0721-4d34-ab5d-2b913af4207e"),
                "bbbbbbb",
                LocalDateTime.of(2024, 8, 22, 11, 35, 59),
                null,
                UUID.fromString("da6cc511-51b7-43e4-8cf0-e35bfc3b2d22"), UUID.fromString("3ae01670-78ee-46f0-bba0-8a86081220bc")
        );

        entityManager.persist(message);
        entityManager.flush();
    }

    @AfterEach
    public void tearDown() {
        entityManager.clear();
    }

    @Test
//    @Sql("classpath:data.sql")
    void testFindMessage() {


        UUID messageId = message.getMessageId();
        Message foundMessage = messageRepository.findByMessageId(messageId);

        System.out.println("Message ID: " + messageId);
        System.out.println("Found Message: " + foundMessage);

        Assertions.assertNotNull(foundMessage, "The message should not be null");
        Assertions.assertEquals(message.getText(), foundMessage.getText(), "The text should match");

    }
}


