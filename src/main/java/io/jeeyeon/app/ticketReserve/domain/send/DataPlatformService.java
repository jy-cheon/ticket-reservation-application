package io.jeeyeon.app.ticketReserve.domain.send;

import io.jeeyeon.app.ticketReserve.domain.payment.event.PaymentEvent;
import org.springframework.stereotype.Service;

@Service
public class DataPlatformService {

    public void send(PaymentEvent paymentEvent) {
        // 결제 정보 전송 로직 구현
        System.out.println("Sending payment info: " + paymentEvent);
    }
}