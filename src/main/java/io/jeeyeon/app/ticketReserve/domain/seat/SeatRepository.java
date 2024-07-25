package io.jeeyeon.app.ticketReserve.domain.seat;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository {

    List<Seat> findByConcertScheduleId(Long concertScheduleId);


    List<Seat> findByConcertScheduleIdAndStatus(Long concertScheduleId, SeatStatus status);

    Optional<Seat> findByConcertScheduleIdAndSeatNumber(Long scheduleId, String seatNumber);

    Seat save(Seat seat);

    void updateSeatStatusInBatch(List<Long> seatIds, SeatStatus available);

    Optional<Seat> findById(Long seatId);
}
