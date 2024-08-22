package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.payment.event.PaymentEvent;
import io.jeeyeon.app.ticketReserve.domain.send.DataPlatformService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataPlatformFacade {
    private final DataPlatformService dataPlatformService;

    public void send(PaymentEvent paymentEvent) {
        dataPlatformService.send(paymentEvent);
    }



}
