package io.jeeyeon.app.ticketReserve.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    public Payment save(Long reservationId, Integer ticketPrice) {
         return paymentRepository.save(new Payment(reservationId, ticketPrice));
    }
}
