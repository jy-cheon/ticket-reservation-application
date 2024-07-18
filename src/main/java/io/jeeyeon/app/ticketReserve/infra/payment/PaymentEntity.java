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
    private Integer amount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PaymentEntity(Payment payment) {
        this.reservationId = payment.getReservationId();
        this.amount = payment.getAmount();
    }

    public Payment toPayment() {
        Payment payment = new Payment();
        payment.setPaymentId(this.paymentId);
        payment.setReservationId(this.reservationId);
        payment.setAmount(this.amount);
        payment.setCreatedAt(this.createdAt);
        payment.setUpdatedAt(this.updatedAt);
        return payment;
    }
}
