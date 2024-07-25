package io.jeeyeon.app.ticketReserve.infra.seat;

import io.jeeyeon.app.ticketReserve.domain.seat.Seat;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatRepository;
import io.jeeyeon.app.ticketReserve.domain.seat.SeatStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
public class SeatRepositoryImpl implements SeatRepository {
    private final SeatJpaRepository seatJpaRepository;
    @Override
    public List<Seat> findByConcertScheduleId(Long concertScheduleId) {
        return seatJpaRepository.findByConcertScheduleId(concertScheduleId).stream()
                .map(SeatEntity::toSeat).collect(Collectors.toList());
    }

    @Override
    public List<Seat> findByConcertScheduleIdAndStatus(Long concertScheduleId, SeatStatus status) {
        return seatJpaRepository.findByConcertScheduleIdAndStatus(concertScheduleId, status).stream()
                .map(SeatEntity::toSeat).collect(Collectors.toList());
    }

    @Override
    public Optional<Seat> findByConcertScheduleIdAndSeatNumber(Long scheduleId, String seatNumber) {
        return seatJpaRepository.findByConcertScheduleIdAndSeatNumber(scheduleId, seatNumber).stream()
                .map(SeatEntity::toSeat)
                .findAny();
    }

    @Override
    public Seat save(Seat seat) {
        return seatJpaRepository.save(new SeatEntity(seat)).toSeat();
    }

    @Override
    public void updateSeatStatusInBatch(List<Long> seatIds, SeatStatus available) {
        seatJpaRepository.updateSeatStatusInBatch(seatIds, available);
    }

    @Override
    public Optional<Seat> findById(Long seatId) {
        return seatJpaRepository.findById(seatId).stream()
                .map(SeatEntity::toSeat).findAny();
    }
}
