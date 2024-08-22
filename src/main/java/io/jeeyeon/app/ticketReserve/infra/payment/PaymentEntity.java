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
    @Column(name = "PAYMENT_ID")
    private Long paymentId;
    @Column(name = "RESERVATION_ID")
    private Long reservationId;
    @Column(name = "AMOUNT")
    private Integer amount;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name = "UPDATED_AT")
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
