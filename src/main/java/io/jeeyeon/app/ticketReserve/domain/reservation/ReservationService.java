package io.jeeyeon.app.ticketReserve.domain.reservation;

import io.jeeyeon.app.ticketReserve.domain.common.exception.BaseException;
import io.jeeyeon.app.ticketReserve.domain.common.exception.ErrorType;
import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatRepository;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public Reservation reserve(Long scheduleId, String seatNumber, Long tokenId) {
        // 좌석 유효성 체크
        Seat seat = seatRepository.findByConcertScheduleIdAndSeatNumber(scheduleId, seatNumber)
                .orElseThrow(() -> new BaseException(ErrorType.SEAT_ENTITY_NOT_FOUND));

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 좌석 예약(좌석배정)
        if (seat.isAvailable()) {
            seat.setStatus(SeatStatus.RESERVED);
            seatRepository.save(seat);
        } else {
            log.warn("해당 예약은 이미 예약되었습니다. {}", seatNumber);
            throw new BaseException(ErrorType.NOT_AVAILABLE_SEAT);
        }

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 예약 저장
        return reservationRepository.save(new Reservation(seat.getSeatId(), tokenId));
    }

    public List<Reservation> findUnpaidReservations() {
        LocalDateTime fiveMinutesAgo = LocalDateTime.now().minusMinutes(5);
        List<Reservation> list = reservationRepository.findUnpaidReservations(fiveMinutesAgo);
        return list;
    }

    public List<Reservation> cancelReservations(List<Reservation> reservations) {
        for (Reservation r : reservations) {
            r.cancel();
        }
        reservationRepository.saveAll(reservations);
        return reservations;
    }

    public Optional<Reservation> findById(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }

    public void confirm(Reservation reservation) {
        reservation.confirm();
        reservationRepository.save(reservation);
    }
}
