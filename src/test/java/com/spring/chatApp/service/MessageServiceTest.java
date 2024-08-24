package com.spring.chatApp.service;


import com.spring.chatApp.data.RowMapper.MessageRowMapper;
import com.spring.chatApp.data.model.Message;
import com.spring.chatApp.data.model.User;
import com.spring.chatApp.data.repository.MessageRepository;
import com.spring.chatApp.data.repository.UserRepository;
import com.spring.chatApp.dto.MessageDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageServiceTest {


    Message message = new Message(UUID.fromString("7e4c2f7c-0711-4d34-ab0d-2b913af4207e"),
            "aaaaaaa",
            LocalDateTime.of(2024, 8, 22, 11, 35, 59),
            null,
            UUID.fromString("da6cc511-51b7-43e4-8cf0-e35bfc3b2d22"), UUID.fromString("3ae01670-78ee-46f0-bba0-8a86081220bc")
    );
    List<Message> messages = Collections.singletonList(message);
    MessageDto messageDto = new MessageDto(UUID.fromString("7e4c2f7c-0711-4d34-ab0d-2b913af4207e"),
            "aaaaaaa",
            LocalDateTime.of(2024, 8, 22, 11, 35, 59),
            null,
            UUID.fromString("da6cc511-51b7-43e4-8cf0-e35bfc3b2d22"), UUID.fromString("3ae01670-78ee-46f0-bba0-8a86081220bc")
    );
    ;
    List<MessageDto> messageDtos = Collections.singletonList(messageDto);
    @Mock
    private JdbcTemplate jdbcTemplate;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private MessageService messageService;
    private AutoCloseable autocloseable;

    @BeforeEach
    public void setUp() throws Exception {
        autocloseable = MockitoAnnotations.openMocks(this);

    }

    @AfterEach
    void tearDown() throws Exception {
        autocloseable.close();
    }

    @Test
    void testReceivedMessages() {

        User mockUser = mock(User.class);

        when(userRepository.findMessageByUsername("user1")).thenReturn(mockUser);

        when(mockUser.getReceivedMessages()).thenReturn(messages);

        assertThat(messageService.receivedMessages("user1")).isEqualTo(messages);


    }


    @Test
    void testSentMessages() {
        User mockUser = mock(User.class);

        when(userRepository.findMessageByUsername("user1")).thenReturn(mockUser);

        when(mockUser.getSentMessages()).thenReturn(Collections.emptyList());

        assertThat(messageService.sentMessages("user1")).isEqualTo(Collections.emptyList());
    }


    @Test
    void twoWayChat() {

        User mockUser1 = new User(UUID.fromString("da6cc511-51b7-43e4-8cf0-e35bfc3b2d22"), "user", "", true, Collections.emptyList(), Collections.emptyList(), Collections.singletonList(null));
        User mockUser2 = new User(UUID.fromString("3ae01670-78ee-46f0-bba0-8a86081220bc"), "user1", "", true, Collections.emptyList(), Collections.emptyList(), Collections.singletonList(null));


        when(userRepository.findMessageByUsername("user")).thenReturn(mockUser1);
        when(userRepository.findByUsername("user1")).thenReturn(mockUser2);

        String userId1 = mockUser1.getId().toString();
        String userId2 = mockUser2.getId().toString();
        when(jdbcTemplate.query(
                "select * from chat.message where (sender_id = uuid_to_bin(?) and recipient_id = uuid_to_bin(?)) or (sender_id = uuid_to_bin(?) and recipient_id = uuid_to_bin(?)) order by message.sent_date",
                new MessageRowMapper(),
                userId1, userId2, userId2, userId1)).thenReturn(messageDtos);

        assertThat(messageService.twoWayChat("user1", "user")).isEqualTo(Collections.emptyList());
    }


}
