package io.jeeyeon.app.ticketReserve.infra.payment;

import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessage;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentKafkaMessageProducer implements PaymentMessageSender {
    private final KafkaTemplate<String, PaymentMessage> kafkaTemplate;

    @Value("${payment.topic}")
    private String PAYMENT_TOPIC;

    public PaymentKafkaMessageProducer(KafkaTemplate<String, PaymentMessage> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(PaymentMessage paymentMessage) {
        kafkaTemplate.send(PAYMENT_TOPIC, paymentMessage);
    }
}
