package io.jeeyeon.app.ticketReserve.infra.payment;

import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessage;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "PAYMENT_OUTBOX")
public class PaymentOutboxEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "MESSAGE")
    private String message;
    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_MESSAGE_STATUS")
    private PaymentMessageStatus status;
    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    public PaymentOutboxEntity(PaymentMessage paymentMessage) {
        this.id = paymentMessage.getId();
        this.message = paymentMessage.getMessage();
        this.status = paymentMessage.getStatus();
    }

    public PaymentMessage toPaymentMessage() {
        PaymentMessage paymentMessage = new PaymentMessage();
        paymentMessage.setId(this.id);
        paymentMessage.setMessage(this.message);
        paymentMessage.setStatus(this.status);
        paymentMessage.setCreatedAt(this.createdAt);
        paymentMessage.setUpdatedAt(this.updatedAt);
        return paymentMessage;
    }
}
