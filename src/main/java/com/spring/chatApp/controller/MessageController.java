package com.spring.chatApp.controller;

import com.spring.chatApp.data.model.Message;
import com.spring.chatApp.dto.MessageDto;
import com.spring.chatApp.service.MessageService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;


    @GetMapping("/user/{peer}/chat-messages")
    public List<MessageDto> twoWayChatMessages(@PathVariable String peer, Authentication authentication) {
        return messageService.twoWayChat(peer, authentication);
    }


    @GetMapping("/user/received-messages")
    public List<Message> receivedMessages(Authentication authentication) {
        return messageService.receivedMessages(authentication.getName());
    }

    @GetMapping("/user/sent-messages")
    public List<Message> sentMessages(Authentication authentication) {

        return messageService.sentMessages(authentication);
    }

    @GetMapping("/user/get-message")
    public Message getMessage(@RequestParam UUID id) {
        return messageService.getMessage(id);

    }

    @GetMapping("/user/update-message")
    public Message showUpdatePage(@RequestParam UUID id) {

        return messageService.showUpdatePage(id);
    }

    @PostMapping("/user/update-message")
    public ResponseEntity<String> updateMessage(@RequestBody @Valid Message msg,
                                                @RequestParam UUID id, BindingResult result) {

        return messageService.updateMessage(msg, id, result);
    }

    @PostMapping(path = "/user/{peer}/send-message")
    public ResponseEntity<String> sendMessage(
            @RequestBody @Valid Message msg,
            @PathVariable String peer,
            Authentication authentication) {

        return messageService.sendMessage(msg, peer, authentication);
    }

    @DeleteMapping("/user/delete-message")
    public ResponseEntity<String> deleteMessage(@RequestParam UUID id) {

        return messageService.deleteMessage(id);
    }

}
