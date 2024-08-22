package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessage;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageOutboxWriter;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PaymentOutboxManagerFacadeTest {

    @Mock
    private PaymentMessageOutboxWriter paymentMessageOutboxWriter;

    @InjectMocks
    private PaymentOutboxManagerFacade paymentOutboxManagerFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("아웃박스 재발행 스케줄러 로직 테스트")
    @Test
    void testFindAllFailedMessages() {
        // Given
        PaymentMessage oldMessage = new PaymentMessage();
        oldMessage.setId(1L);
        oldMessage.setStatus(PaymentMessageStatus.INIT);
        oldMessage.setCreatedAt(LocalDateTime.now().minusMinutes(10)); // 10 minutes old

        PaymentMessage recentMessage = new PaymentMessage();
        recentMessage.setId(2L);
        recentMessage.setStatus(PaymentMessageStatus.INIT);
        recentMessage.setCreatedAt(LocalDateTime.now().minusMinutes(4)); // 4 minutes old

        List<PaymentMessage> messages = Arrays.asList(oldMessage, recentMessage);

        when(paymentMessageOutboxWriter.findAllByStatus(PaymentMessageStatus.INIT)).thenReturn(messages);

        // When
        List<PaymentMessage> failedMessages = paymentOutboxManagerFacade.findAllFailedMessages();

        // Then
        assertEquals(1, failedMessages.size(), "Should only include messages older than 5 minutes");
        assertEquals(1L, failedMessages.get(0).getId(), "The ID of the message should be 1");
    }
}