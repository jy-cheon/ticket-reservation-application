package io.jeeyeon.app.ticketReserve.domain.payment.event;

import org.springframework.stereotype.Component;

@Component
public interface PaymentEventPublisher {

    void publish(PaymentEvent paymentEvent);
}