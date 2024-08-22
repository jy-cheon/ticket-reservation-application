package io.jeeyeon.app.ticketReserve.infra.payment;

import io.jeeyeon.app.ticketReserve.domain.payment.event.PaymentEvent;
import io.jeeyeon.app.ticketReserve.domain.payment.event.PaymentEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentSpringEventPublisher implements PaymentEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;
    @Override
    public void publish(PaymentEvent paymentEvent) {
        applicationEventPublisher.publishEvent(paymentEvent);
    }
}
