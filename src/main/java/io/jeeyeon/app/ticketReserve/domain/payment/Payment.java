package io.jeeyeon.app.ticketReserve.domain.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Payment {
    private Long reservationId;
    private Long amount;

    public Payment(Long reservationId, Long amount) {
        this.reservationId = reservationId;
        this.amount = amount;
    }
}
