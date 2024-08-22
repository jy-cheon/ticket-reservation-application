package io.jeeyeon.app.ticketReserve.interfaces.scheduler;

import io.jeeyeon.app.ticketReserve.application.PaymentOutboxManagerFacade;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessage;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class PaymentSenderScheduler {

    private final PaymentOutboxManagerFacade paymentOutboxManager;
    private final PaymentMessageSender paymentMessageSender;

    @Scheduled(fixedRate = 60 * 1000)
    void resendFailedMessage() {
        List<PaymentMessage> paymentMessage = paymentOutboxManager.findAllFailedMessages();
        paymentMessage.forEach(it -> paymentMessageSender.send(it));
    }

}
