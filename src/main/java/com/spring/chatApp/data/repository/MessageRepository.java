package com.spring.chatApp.data.repository;


import com.spring.chatApp.data.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    Message findByMessageId(UUID messageId);

}
