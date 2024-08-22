package com.spring.chatApp;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MessageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void simpleGetPeerMessages() throws Exception {
        mockMvc.perform(get("/user/user1/chat-messages")
                        .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJzdWIiOiJ1c2VyIiwiZXhwIjoxNzMwMDQwNTk0LCJpYXQiOjE3MjQwNDA1OTQsInNjb3BlIjoiQURNSU4ifQ.RWdbRPETe-1g1TyA2RtN16DBtkNnY9N5-jyPsa6zYhE"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(3)))
                .andExpect(jsonPath("$.[0].messageId", Matchers.is("8cfc2515-55ab-4bcc-bf05-991956b15755")))
        ;

    }
}
