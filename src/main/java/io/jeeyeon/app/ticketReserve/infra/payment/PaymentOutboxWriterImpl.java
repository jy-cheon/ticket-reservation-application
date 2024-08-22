package io.jeeyeon.app.ticketReserve.infra.payment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jeeyeon.app.ticketReserve.domain.payment.event.PaymentEvent;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessage;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageOutboxWriter;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class PaymentOutboxWriterImpl implements PaymentMessageOutboxWriter {
    private final PaymentJpaOutboxWriter paymentJpaOutboxWriter;
    private final ObjectMapper objectMapper;

    @Override
    public void save(PaymentMessage paymentMessage) {
        paymentJpaOutboxWriter.save(new PaymentOutboxEntity(paymentMessage));
    }

    @Override
    public void complete(PaymentMessage paymentMessage) throws JsonProcessingException {

        PaymentEvent paymentEvent = objectMapper.readValue(paymentMessage.getMessage(), PaymentEvent.class);


        PaymentOutboxEntity existingMessage = paymentJpaOutboxWriter.findById(paymentEvent.getPaymentId())
                .orElseThrow(() -> new EntityNotFoundException("PaymentMessage not found"));
        existingMessage.setStatus(PaymentMessageStatus.PUBLISHED);
        existingMessage.setUpdatedAt(LocalDateTime.now());
        paymentJpaOutboxWriter.save(existingMessage);
    }

    @Override
    public List<PaymentMessage> findAllByStatus(PaymentMessageStatus status) {
        List<PaymentOutboxEntity> list = paymentJpaOutboxWriter.findAllByStatus(status);
        return list.stream()
                .map(paymentOutboxEntity -> paymentOutboxEntity.toPaymentMessage())
                .collect(Collectors.toList());
    }

}
