package com.spring.chatApp.data.repository;


import com.spring.chatApp.data.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Repository
@Transactional
public interface MessageRepository extends JpaRepository<Message, UUID> {


    Message findByMessageId(UUID messageId);

    @Query(value = "select * from message where (sender_id = ?1 and recipient_id = ?2) or (sender_id = ?3 and recipient_id = ?4) order by message.sent_date", nativeQuery = true)
    List<Message> findBySenderIdAndRecipientId(UUID senderId1, UUID recipientId1, UUID recipientId2, UUID senderId2);

}
