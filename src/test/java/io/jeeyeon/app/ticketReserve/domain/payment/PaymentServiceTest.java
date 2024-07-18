package io.jeeyeon.app.ticketReserve.domain.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("결제 정보 저장 테스트")
    public void testSavePayment() {
        // given
        Long reservationId = 1L;
        Integer ticketPrice = 5000;

        // when
        paymentService.save(reservationId, ticketPrice);

        // then
        verify(paymentRepository, times(1)).save(any(Payment.class)); // save 메서드가 1번 호출되어야 함
    }
}