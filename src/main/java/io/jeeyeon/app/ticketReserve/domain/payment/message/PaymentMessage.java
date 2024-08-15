package io.jeeyeon.app.ticketReserve.domain.payment.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.jeeyeon.app.ticketReserve.domain.payment.event.PaymentEvent;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PaymentMessage {
    private Long id;
    private String message;
    private PaymentMessageStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PaymentMessage from(PaymentEvent event) throws JsonProcessingException {
        PaymentMessage paymentMessage = new PaymentMessage();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String jsonString = objectMapper.writeValueAsString(event);

        paymentMessage.id = event.getPaymentId();
        paymentMessage.message = jsonString;
        paymentMessage.status = PaymentMessageStatus.INIT;
        return paymentMessage;
    }
}
