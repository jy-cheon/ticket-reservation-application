package io.jeeyeon.app.ticketReserve.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    public void save(Long reservationId, Long ticketPrice) {
        paymentRepository.save(new Payment(reservationId, ticketPrice));
    }
}
