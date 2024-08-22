package com.spring.chatApp.service;

import com.spring.chatApp.data.RowMapper.MessageRowMapper;
import com.spring.chatApp.data.model.Message;
import com.spring.chatApp.data.model.User;
import com.spring.chatApp.data.repository.MessageRepository;
import com.spring.chatApp.data.repository.UserRepository;
import com.spring.chatApp.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class MessageService {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    MessageDto messageDto;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    public List<MessageDto> twoWayChat(String peer, Authentication authentication) {

        String senderId1 = userRepository.findMessageByUsername(authentication.getName()).getId().toString();
        String recipientId1 = userRepository.findByUsername(peer).getId().toString();

        List<MessageDto> query = jdbcTemplate.query(
                "select * from message where (sender_id = uuid_to_bin(?) and recipient_id = uuid_to_bin(?)) or (sender_id = uuid_to_bin(?) and recipient_id = uuid_to_bin(?)) order by message.sent_date",
                new MessageRowMapper(),
                senderId1, recipientId1, recipientId1, senderId1);
        return query;

    }

    public List<Message> receivedMessages(String username) {
        return userRepository.findMessageByUsername(username).getReceivedMessages();
    }

    public List<Message> sentMessages(Authentication authentication) {

        return userRepository.findMessageByUsername(authentication.getName()).getSentMessages();
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


    public ResponseEntity<String> sendMessage(Message msg, String peer, Authentication authentication) {

        User user = userRepository.findByUsername(authentication.getName());
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


