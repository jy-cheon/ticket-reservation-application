package io.jeeyeon.app.ticketReserve.infra.payment;

import io.jeeyeon.app.ticketReserve.domain.payment.Payment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "PAYMENT")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    private Long reservationId;
    private Long amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PaymentEntity(Payment payment) {
        this.reservationId = payment.getReservationId();
        this.amount = payment.getAmount();
    }
}
