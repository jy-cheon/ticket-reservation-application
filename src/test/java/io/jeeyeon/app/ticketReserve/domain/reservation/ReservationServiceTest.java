package io.jeeyeon.app.ticketReserve.domain.reservation;

import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatRepository;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("이미 예약된 좌석을 다시 예약할 수 없음")
    public void testReserve_alreadyReservedSeat() {
        // given
        Long scheduleId = 1L;
        String seatNumber = "A101";
        Long tokenId = 1L;

        Seat reservedSeat = new Seat(scheduleId, seatNumber, SeatStatus.RESERVED);
        when(seatRepository.findByConcertScheduleIdAndSeatNumber(scheduleId, seatNumber))
                .thenReturn(reservedSeat);

        // when & then
        assertThrows(IllegalStateException.class, () -> {
            reservationService.reserve(scheduleId, seatNumber, tokenId);
        });
    }

//    @Test
//    @DisplayName("예약 만료 확인")
//    public void testExpiredReservation() {
//        // given
//        Long reservationId = 1L;
//        LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(10); // 예약 만료 시간 설정
//
//        Reservation expiredReservation = new Reservation();
//        expiredReservation.setReservationId(reservationId);
//        expiredReservation.setCreatedAt(expiredTime.minusMinutes(30)); // 예약 생성 시간 설정
//
//        // mockito stubbing
//        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(expiredReservation));
//
//        // when
//        reservationService.findUnpaidReservations(); // 예약 만료 확인 메서드 호출
//
//        // then
//        verify(reservationRepository, times(1)).findById(reservationId); // findById 메서드가 1번 호출되어야 함
//        verify(reservationRepository, times(1)).save(expiredReservation); // save 메서드가 1번 호출되어야 함
//        assertEquals(ReservationStatus.CANCELLED, expiredReservation.getStatus()); // 예약 상태가 CANCELLED 여야 함
//    }
}
