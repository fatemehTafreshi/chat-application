package com.spring.chatApp.controller;


import com.spring.chatApp.data.model.Message;
import com.spring.chatApp.service.MessageService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
public class MessageControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageService messageService;


    @Test
    public void testReceivedMessages() throws Exception {
        List<Message> mockMessages = Arrays.asList(
                new Message(
                        UUID.fromString("7e4c2f7c-0711-4d34-ab0d-2b913af4207e"),
                        "aaaaaaa",
                        LocalDateTime.of(2024, 8, 22, 11, 35, 59),
                        null,
                        UUID.fromString("da6cc511-51b7-43e4-8cf0-e35bfc3b2d22"), UUID.fromString("3ae01670-78ee-46f0-bba0-8a86081220bc"))
        );


        when(messageService.receivedMessages("user2")).thenReturn(mockMessages);

        mockMvc.perform(get("/user/received-messages")
                        .with(jwt().jwt(jwt -> jwt.claim("scope", "read").subject("user2")))
                        .accept("application/json")
                        .contentType(MediaType.APPLICATION_JSON)
                )

                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].text").value("aaaaaaa"));
    }



}