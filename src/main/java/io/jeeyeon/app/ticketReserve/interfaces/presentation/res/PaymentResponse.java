package io.jeeyeon.app.ticketReserve.interfaces.presentation.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
public class PaymentResponse {
    private Long paymentId;
    private LocalDateTime paymentTime;
}
