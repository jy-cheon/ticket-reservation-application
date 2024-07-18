package io.jeeyeon.app.ticketReserve.domain.payment;

import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository {
    Payment save(Payment payment);
}
