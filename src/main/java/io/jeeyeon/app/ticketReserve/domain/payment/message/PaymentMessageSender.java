package io.jeeyeon.app.ticketReserve.domain.payment.message;

import org.springframework.stereotype.Component;

@Component
public interface PaymentMessageSender {
    void send(PaymentMessage paymentMessage);
}
