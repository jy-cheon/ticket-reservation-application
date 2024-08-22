package io.jeeyeon.app.ticketReserve.interfaces.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessage;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageOutboxWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentMessageConsumer {
    // 아웃박스 마킹
    private final PaymentMessageOutboxWriter paymentMessageOutboxWriter;

    @KafkaListener(topics = "${payment.topic}", groupId = "outboxGroup")
    protected void complete(PaymentMessage message) throws JsonProcessingException {
        paymentMessageOutboxWriter.complete(message);
    }
}
