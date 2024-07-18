package io.jeeyeon.app.ticketReserve.infra.payment;

import io.jeeyeon.app.ticketReserve.domain.payment.Payment;
import io.jeeyeon.app.ticketReserve.domain.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PaymentRepositoryImpl implements PaymentRepository {
    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity paymentEntity = paymentJpaRepository.save(new PaymentEntity(payment));
        return paymentEntity.toPayment();
    }
}
