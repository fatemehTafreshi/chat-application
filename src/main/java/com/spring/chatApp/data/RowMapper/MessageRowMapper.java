package com.spring.chatApp.data.RowMapper;

import com.spring.chatApp.dto.MessageDto;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.spring.chatApp.configuration.CommonMethods.uuidFixEndian;

@Component
public class MessageRowMapper implements RowMapper<MessageDto> {




    @Override
    public MessageDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        MessageDto msg = new MessageDto();

        msg.setId(uuidFixEndian(rs.getBytes("message_id")));
        msg.setText(rs.getString("text"));

        msg.setSeenDate(rs.getObject("seen_date", LocalDateTime.class));
        msg.setSentDate(rs.getObject("sent_date", LocalDateTime.class));


        UUID sender_UUID = uuidFixEndian(rs.getBytes("sender_id"));
        msg.setSenderId(sender_UUID);
//        msg.setSenderName(userRepository.findById(sender_UUID).get().getUsername());


        UUID recipient_UUID = uuidFixEndian(rs.getBytes("sender_id"));
        msg.setRecipientId(recipient_UUID);
//        msg.setRecipientName(userRepository.findById(recipient_UUID).get().getUsername());


        return msg;
    }


}