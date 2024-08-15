package io.jeeyeon.app.ticketReserve.interfaces.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jeeyeon.app.ticketReserve.application.DataPlatformFacade;
import io.jeeyeon.app.ticketReserve.domain.payment.event.PaymentEvent;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessage;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageOutboxWriter;
import io.jeeyeon.app.ticketReserve.domain.payment.message.PaymentMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@RequiredArgsConstructor
@Component
public class PaymentEventListener {
    private final DataPlatformFacade dataPlatformSender;
    private final PaymentMessageSender messageSender;
    private final PaymentMessageOutboxWriter outboxWriter;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendPaymentMessage(PaymentEvent paymentEvent) {
        // 성공한 결제 정보 전송
        dataPlatformSender.send(paymentEvent);
    }

    // 커밋이 완료되었다면, 메세지를 발행해야 됨
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    void publishMessage(PaymentEvent event) throws JsonProcessingException {
        log.info("메세지 발행");
        messageSender.send(PaymentMessage.from(event));
    }

    // 같은 트랜잭션 안에서 커밋이 함께 되는 걸 보장해야함
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    void saveOutbox(PaymentEvent event) throws JsonProcessingException {
        log.info("아웃박스 발행");
        outboxWriter.save(PaymentMessage.from(event));
    }
}
