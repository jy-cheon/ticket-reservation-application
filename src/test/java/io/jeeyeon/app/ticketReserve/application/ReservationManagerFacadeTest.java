package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.reservation.Reservation;
import io.jeeyeon.app.ticketReserve.domain.reservation.ReservationRepository;
import io.jeeyeon.app.ticketReserve.domain.reservation.ReservationStatus;
import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatRepository;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@SpringBootTest
@Transactional
class ReservationManagerFacadeTest {
    @Autowired
    private ReservationManagerFacade reservationManagerFacade;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeatRepository seatRepository;


    @Test
    @Sql("/data-test.sql")
    @DisplayName("예약한지 5분이상 지난 예약건을 취소시 성공한다")
    public void testCancelReservations() {
        // Given: 예약 2건 준비

        // When: 예약 취소 메서드 실행
        reservationManagerFacade.cancelReservations();

        // Then: 좌석이 회수되었는지 확인
        Reservation reservation1 = reservationRepository.findById(2l).get();
        long seatId = reservation1.getSeatId();
        Seat seat = seatRepository.findById(seatId).get();

        Assertions.assertEquals(ReservationStatus.CANCELLED, reservation1.getStatus());
        Assertions.assertEquals(SeatStatus.AVAILABLE, seat.getStatus());
    }

    @Test
    @Sql("/data-test.sql")
    @DisplayName("이미 취소된 예약을 취소 시도시에 실패한다.")
    public void testCancelReservations2() {
        // Given: 예약 2건 준비

        // When: 예약 취소 메서드 실행
        reservationManagerFacade.cancelReservations();

        // Then: 좌석이 회수되었는지 확인
        Reservation reservation1 = reservationRepository.findById(3l).get();
        long seatId = reservation1.getSeatId();
        Seat seat = seatRepository.findById(seatId).get();

        Assertions.assertEquals(ReservationStatus.CANCELLED, reservation1.getStatus());
        Assertions.assertEquals(SeatStatus.AVAILABLE, seat.getStatus());
    }

    @Test
    @Sql("/data-test2.sql")
    @DisplayName("좌석 예약 요청 성공한다.")
    public void test_applyForReservation() {
        // Given: 예약 1건 준비
        Long concertId = 2l;
        String date = "2025-07-17 18:30:00";
        String seatNumber = "A02";
        Long tokenId = 7l;

        // When: 좌석 예약 요청
        reservationManagerFacade.applyForReservation(concertId, date, seatNumber, tokenId);

        // Then: 좌석이 예약이 되었는지 확인
        List<Reservation> reservations = reservationRepository.findAll();
        Reservation reservation1 = reservations.get(reservations.size()-1);
        long seatId = reservation1.getSeatId();
        Seat seat = seatRepository.findById(seatId).get();
        Assertions.assertEquals("A02", seat.getSeatNumber());
    }


}