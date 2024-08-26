package com.spring.chatApp.service;

import com.spring.chatApp.data.model.Message;
import com.spring.chatApp.data.model.User;
import com.spring.chatApp.data.repository.MessageRepository;
import com.spring.chatApp.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Message> twoWayChat(String peer, String username) {

        UUID senderId1 = userRepository.findMessageByUsername(username).getId();
        UUID recipientId1 = userRepository.findByUsername(peer).getId();

        return messageRepository.findBySenderIdAndRecipientId(senderId1, recipientId1, recipientId1, senderId1);

    }

    public List<Message> receivedMessages(String username) {
        return userRepository.findMessageByUsername(username).getReceivedMessages();
    }

    public List<Message> sentMessages(String username) {

        return userRepository.findMessageByUsername(username).getSentMessages();
    }

    public ResponseEntity<String> updateMessage(Message msg,
                                                UUID id, BindingResult result) {

        if (result.hasErrors()) {
//            return "/user/received-messages";
        }
        Message msg1 = messageRepository.findByMessageId(id);
        if (msg1.getText().matches(msg.getText())) {

            return new ResponseEntity<>(
                    "Your entered text is same as before!",
                    HttpStatus.BAD_REQUEST);
        } else {
            msg1.setText(msg.getText());
            messageRepository.save(msg1);
            return ResponseEntity.ok("Message updated successfully!");
        }
    }


    public ResponseEntity<String> sendMessage(Message msg, String peer, String username) {

        User user = userRepository.findByUsername(username);
        msg.setSenderId(user.getId());
        msg.setRecipientId(userRepository.findMessageByUsername(peer).getId());
        msg.setSentDate(LocalDateTime.now());

        messageRepository.save(msg);
        return ResponseEntity.ok("Message sent successfully!");
    }

    @Transactional
    public Message getMessage(UUID id) {
        return messageRepository.findByMessageId(id);

    }

    @Transactional
    public ResponseEntity<String> deleteMessage(UUID id) {
        messageRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleted the message");
    }

    @Transactional
    public Message showUpdatePage(UUID id) {

        return messageRepository.findByMessageId(id);
    }

}


