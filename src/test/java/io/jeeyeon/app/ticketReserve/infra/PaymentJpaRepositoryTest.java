package io.jeeyeon.app.ticketReserve.infra;

import io.jeeyeon.app.ticketReserve.infra.payment.PaymentEntity;
import io.jeeyeon.app.ticketReserve.infra.payment.PaymentJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest// jpa 간단테스트 해주는
class PaymentJpaRepositoryTest {

    @Autowired
    PaymentJpaRepository paymentJpaRepository;

    @Test
    @DisplayName("결제이력 저장")
    void 결제이력저장() {
        //given
        PaymentEntity entity = new PaymentEntity();
        entity.setPaymentId(1L);
        entity.setReservationId(2L);
        entity.setAmount(10000);

        //when
        PaymentEntity save = paymentJpaRepository.save(entity);

        //then
        Assertions.assertEquals(entity.getPaymentId(), save.getPaymentId());
        Assertions.assertEquals(entity.getReservationId(), save.getReservationId());
        Assertions.assertEquals(entity.getAmount(), save.getAmount());
    }

}