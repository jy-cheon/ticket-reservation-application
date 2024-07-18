package io.jeeyeon.app.ticketReserve.domain.reservation;

import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatRepository;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final SeatRepository seatRepository;

    @Transactional
    public void reserve(Long scheduleId, String seatNumber, Long tokenId) {
        // 좌석 유효성 체크
        Seat seat = seatRepository.findByConcertScheduleIdAndSeatNumber(scheduleId, seatNumber);

        // 좌석 예약(좌석배정)
        if (seat.isAvailable()) {
            seat.setStatus(SeatStatus.RESERVED);
            seatRepository.save(seat);
        } else {
            throw new IllegalStateException("Seat number " + seatNumber + " is not available for reservation.");
        }

        // 예약 저장
        reservationRepository.save(new Reservation(seat.getSeatId(), tokenId));
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
