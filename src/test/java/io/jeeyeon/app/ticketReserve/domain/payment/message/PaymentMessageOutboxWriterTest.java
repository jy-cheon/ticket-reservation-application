package io.jeeyeon.app.ticketReserve.domain.payment.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeeyeon.app.ticketReserve.domain.payment.event.PaymentEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class PaymentMessageOutboxWriterTest {

    @Autowired
    private PaymentMessageOutboxWriter paymentMessageOutboxWriter;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Perform any necessary setup before each test, such as clearing the database
        // This is necessary if you're using an in-memory database or a real database with test data
    }

    @DisplayName("아웃박스 발행 테스트")
    @Test
    void testSave() throws JsonProcessingException {
        // Given
        PaymentMessage paymentMessage = new PaymentMessage();
        paymentMessage.setId(1L);
        paymentMessage.setMessage(objectMapper.writeValueAsString(new PaymentEvent(1L, 1L, 1L, 1000, LocalDateTime.now())));
        paymentMessage.setStatus(PaymentMessageStatus.INIT);
        paymentMessage.setCreatedAt(LocalDateTime.now());
        paymentMessage.setUpdatedAt(LocalDateTime.now());

        // When
        paymentMessageOutboxWriter.save(paymentMessage);

        // Then
        List<PaymentMessage> messages = paymentMessageOutboxWriter.findAllByStatus(PaymentMessageStatus.INIT);
        assertNotNull(messages);
        assertFalse(messages.isEmpty(), "No messages found with status INIT");
        PaymentMessage savedMessage = messages.get(0);
        assertEquals(paymentMessage.getId(), savedMessage.getId(), "Saved message ID does not match");
        assertEquals(paymentMessage.getMessage(), savedMessage.getMessage(), "Saved message content does not match");
    }

    @DisplayName("아웃박스 마킹 테스트")
    @Test
    void testComplete() throws JsonProcessingException {
        // Given
        PaymentMessage paymentMessage = new PaymentMessage();
        paymentMessage.setId(2L);
        paymentMessage.setMessage(objectMapper.writeValueAsString(new PaymentEvent(2L, 2L, 2L, 2000, LocalDateTime.now())));
        paymentMessage.setStatus(PaymentMessageStatus.INIT);
        paymentMessage.setCreatedAt(LocalDateTime.now());
        paymentMessage.setUpdatedAt(LocalDateTime.now());

        paymentMessageOutboxWriter.save(paymentMessage);

        // When
        paymentMessageOutboxWriter.complete(paymentMessage);

        // Then
        List<PaymentMessage> messages = paymentMessageOutboxWriter.findAllByStatus(PaymentMessageStatus.PUBLISHED);
        assertNotNull(messages);
        assertFalse(messages.isEmpty(), "No messages found with status PUBLISHED");
        PaymentMessage completedMessage = messages.get(0);
        assertEquals(paymentMessage.getId(), completedMessage.getId(), "Completed message ID does not match");
        assertEquals(PaymentMessageStatus.PUBLISHED, completedMessage.getStatus(), "Completed message status does not match");
    }
}