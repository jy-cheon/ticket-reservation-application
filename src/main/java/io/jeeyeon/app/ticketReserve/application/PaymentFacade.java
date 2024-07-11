package io.jeeyeon.app.ticketReserve.application;

import io.jeeyeon.app.ticketReserve.domain.payment.PaymentService;
import io.jeeyeon.app.ticketReserve.domain.queueToken.QueueTokenService;
import io.jeeyeon.app.ticketReserve.domain.reservation.Reservation;
import io.jeeyeon.app.ticketReserve.domain.reservation.ReservationService;
import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatService;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import io.jeeyeon.app.ticketReserve.domain.user.UserService;
import jakarta.persistence.EntityNotFoundException;
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

    // 결제
    public void processPayment(Long reservationId, Long userId) throws Exception {
        // 예약 정보 가져오기
        Reservation reservation = reservationService.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found with ID: " + reservationId));

        // 토큰 정보 확인
        queueTokenService.verifyUser(reservation.getTokenId(), userId);

        // 좌석 정보 가져오기
        Seat seat = seatService.findById(reservation.getSeatId())
                .orElseThrow(() -> new EntityNotFoundException("Seat not found with ID: " + reservation.getSeatId()));


        // 유저 잔액 차감
        userService.deductBalance(userId, seat.getTicketPrice());

        // 좌석 상태 변경
        seatService.setStatus(seat, SeatStatus.PAID);

        // 예약 상태 변경
        reservationService.confirm(reservation);

        // 결제 내역 저장
        paymentService.save(reservationId, seat.getTicketPrice());

    }
}
