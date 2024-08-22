package io.jeeyeon.app.ticketReserve.domain.payment.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentMessageOutboxWriter {
    void save(PaymentMessage paymentMessage);

    void complete(PaymentMessage paymentMessage) throws JsonProcessingException;

    List<PaymentMessage> findAllByStatus(PaymentMessageStatus status);
}
