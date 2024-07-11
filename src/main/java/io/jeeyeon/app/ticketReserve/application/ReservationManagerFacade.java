package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.concert.ConcertSchedule;
import io.jeeyeon.app.ticketReserve.domain.concert.ConcertService;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueToken;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueTokenService;
import io.jeeyeon.app.ticketReserve.domain.reservation.Reservation;
import io.jeeyeon.app.ticketReserve.domain.reservation.ReservationService;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReservationManagerFacade {

    private final ReservationService reservationService;
    private final ConcertService concertService;
    private final QueueTokenService queueTokenService;
    private final SeatService seatService;


    // 좌석 예약 요청
    public void applyForReservation(Long concertId, String date, String seatNumber, String userId, Long tokenId) {
        // 토큰 검증 및 대기열 확인
        QueueToken token = queueTokenService.isValidToken(concertId, tokenId);

        // 스케줄 id 확인
        ConcertSchedule schedule = concertService.getConcertSchedule(concertId, date);

        // 좌석 예약
        reservationService.reserve(schedule.getConcertScheduleId(), seatNumber, tokenId);
    }

    // 좌석 배정 취소
    @Transactional
    public void cancelReservations() {
        // 5분내 결제 안된 좌석 찾기
        List<Reservation> unpaidReservations = reservationService.findUnpaidReservations();

        // 예약 상태 변경(취소)
        reservationService.cancelReservations(unpaidReservations);
        List<Long> seatIds = unpaidReservations.stream()
                                .map(r -> r.getSeatId())
                                .collect(Collectors.toList());
        // 좌석 회수
        seatService.revokeSeats(seatIds);
    }

}
