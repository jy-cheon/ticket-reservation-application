package io.jeeyeon.app.ticketReserve.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessage;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageOutboxWriter;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PaymentOutboxManagerFacade {
    private final PaymentMessageOutboxWriter paymentMessageOutboxWriter;
    private final ObjectMapper objectMapper;

    public List<PaymentMessage> findAllFailedMessages() {
        return paymentMessageOutboxWriter.findAllByStatus(PaymentMessageStatus.INIT)
                .stream().filter(paymentMessage -> paymentMessage.getCreatedAt().isBefore(LocalDateTime.now().minusMinutes(5)))
                .collect(Collectors.toList());
    }
}
