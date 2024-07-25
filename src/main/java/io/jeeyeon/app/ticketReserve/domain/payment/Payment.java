package io.jeeyeon.app.ticketReserve.domain.payment;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Payment {
    private Long paymentId;
    private Long reservationId;
    private Integer amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Payment(Long reservationId, Integer amount) {
        this.reservationId = reservationId;
        this.amount = amount;
    }
}
