package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.common.exception.BaseException;
import io.jeeyeon.app.ticketReserve.domain.common.exception.ErrorType;
import io.jeeyeon.app.ticketReserve.domain.concert.ConcertService;
import io.jeeyeon.app.ticketReserve.domain.payment.Payment;
import io.jeeyeon.app.ticketReserve.domain.payment.PaymentService;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueTokenService;
import io.jeeyeon.app.ticketReserve.domain.reservation.Reservation;
import io.jeeyeon.app.ticketReserve.domain.reservation.ReservationService;
import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatService;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import io.jeeyeon.app.ticketReserve.domain.user.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentFacade {
    private final PaymentService paymentService;
    private final ReservationService reservationService;
    private final SeatService seatService;
    private final QueueTokenService queueTokenService;
    private final UserService userService;
    private final ConcertService concertService;

    // 결제
    @Transactional
    public Payment processPayment(Long concertId, Long reservationId, Long userId) throws Exception {
        // 예약 정보 가져오기
        Reservation reservation = reservationService.findById(reservationId)
                .orElseThrow(() -> new BaseException(ErrorType.ENTITY_NOT_FOUND));

        // 좌석 정보 가져오기
        Seat seat = seatService.findById(reservation.getSeatId())
                .orElseThrow(() -> new BaseException(ErrorType.ENTITY_NOT_FOUND));

        // 유저 잔액 차감
        userService.deductBalance(userId, seat.getTicketPrice());

        // 좌석 상태 변경
        seatService.setStatus(seat, SeatStatus.PAID);

        // 예약 상태 변경
        reservationService.confirm(reservation);

        // 토큰 만료
        queueTokenService.expireQueueToken(concertId, userId);

        // 결제 내역 저장
        return paymentService.save(reservationId, seat.getTicketPrice());
    }
}
