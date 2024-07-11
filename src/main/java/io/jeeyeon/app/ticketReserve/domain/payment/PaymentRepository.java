package io.jeeyeon.app.ticketReserve.domain.payment;

import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository {
    void save(Payment payment);
}
