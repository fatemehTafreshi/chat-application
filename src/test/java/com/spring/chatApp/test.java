package com.spring.chatApp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.chatApp.controller.MessageController;
import com.spring.chatApp.data.model.Message;
import com.spring.chatApp.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class test {

    Message message;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MessageService messageService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {

        message = Message.builder()
                .messageId(UUID.fromString("7e4c2f7c-0711-4d34-ab0d-2b913af4207e"))
                .text("testttttttt")
                .sentDate(LocalDateTime.of(2024, 8, 22, 11, 35, 59))
                .seenDate(null)
                .senderId(UUID.fromString("da6cc511-51b7-43e4-8cf0-e35bfc3b2d22"))
                .recipientId(UUID.fromString("3ae01670-78ee-46f0-bba0-8a86081220bc"))
                .build();

    }

    //Post Controller
//    @Test
//    public void saveEmployeeTest() throws Exception{
//        Authentication authentication = mock(Authentication.class);
//        String token="Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJzdWIiOiJ1c2VyMSIsImV4cCI6MTczMDMxOTcxMCwiaWF0IjoxNzI0MzE5NzEwLCJzY29wZSI6IlVTRVIifQ.ObIJ6PZxrXZakfmYKTOpkolOHrKJLfnb5xGjABt_GXI";
//
//        // precondition
////        given(messageService.receivedMessages(authentication).willReturn(message);
//
//        // action
//        ResultActions response = mockMvc.perform(get("/user/received-messages")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(message)));
//
//        // verify
//        response.andDo(print()).
//                andExpect(status().ok())
//                .andExpect(jsonPath("$.messageId",
//                        is(message.getMessageId())))
//                .andExpect(jsonPath("$.text",
//                        is(message.getText())));
////                .andExpect(jsonPath("$.",
////                        is(employee.getEmail())));
//    }

    //Get Controller
    @Test
//    @Order(2)
    public void getEmployeeTest() throws Exception {
        Authentication authentication = mock(Authentication.class);
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJzdWIiOiJ1c2VyMSIsImV4cCI6MTczMDMxOTcxMCwiaWF0IjoxNzI0MzE5NzEwLCJzY29wZSI6IlVTRVIifQ.ObIJ6PZxrXZakfmYKTOpkolOHrKJLfnb5xGjABt_GXI";

        // precondition
        List<Message> messageList = new ArrayList<>();
        messageList.add(message);
//        messageList.add(Message.builder().messageId()
//                .text()
//                .seenDate()
//                .sentDate()
//                .senderId()
//                .recipientId().build());
//        given(messageService.receivedMessages(authentication)).willReturn(messageList);

        // action
        ResultActions response = mockMvc.perform(get("/user/received-messages")
                .with(jwt().jwt(jwt -> jwt.claim("scope", "read")))
                .accept("application/json")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token)
                .principal(authentication));

        // verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(messageList.size())));

    }

    //get by Id controller
//    @Test
////    @Order(3)
//    public void getByIdEmployeeTest() throws Exception {
//        // precondition
//        given(employeeService.getEmployeeById(employee.getId())).willReturn(Optional.of(employee));
//
//        // action
//        ResultActions response = mockMvc.perform(get("/api/employees/{id}", employee.getId()));
//
//        // verify
//        response.andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
//                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
//                .andExpect(jsonPath("$.email", is(employee.getEmail())));
//
//    }
//
//
//    //Update employee
//    @Test
//    @Order(4)
//    public void updateEmployeeTest() throws Exception {
//        // precondition
//        given(employeeService.getEmployeeById(employee.getId())).willReturn(Optional.of(employee));
//        employee.setFirstName("Max");
//        employee.setEmail("max@gmail.com");
//        given(employeeService.updateEmployee(employee, employee.getId())).willReturn(employee);
//
//        // action
//        ResultActions response = mockMvc.perform(put("/api/employees/{id}", employee.getId())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(employee)));
//
//        // verify
//        response.andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
//                .andExpect(jsonPath("$.email", is(employee.getEmail())));
//    }
//
//
//    // delete employee
//    @Test
//    public void deleteEmployeeTest() throws Exception {
//        // precondition
//        willDoNothing().given(employeeService).deleteEmployee(employee.getId());
//
//        // action
//        ResultActions response = mockMvc.perform(delete("/api/employees/{id}", employee.getId()));
//
//        // then - verify the output
//        response.andExpect(status().isOk())
//                .andDo(print());
//    }
}