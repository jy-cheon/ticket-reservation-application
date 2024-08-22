package io.jeeyeon.app.ticketReserve.infra.payment;

import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentJpaOutboxWriter extends JpaRepository<PaymentOutboxEntity, Long> {

    List<PaymentOutboxEntity> findAllByStatus(PaymentMessageStatus status);
}
