package io.jeeyeon.app.ticketReserve.domain.seat;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SeatService {

    private final SeatRepository seatRepository;

    @Transactional
    public void revokeSeats(List<Long> seatIds) {
        seatRepository.updateSeatStatusInBatch(seatIds, SeatStatus.AVAILABLE);
    }

    public Optional<Seat> findById(Long seatId) {
        return seatRepository.findById(seatId);
    }

    public void setStatus(Seat seat, SeatStatus paid) {
        seat.setStatus(paid);
        seatRepository.save(seat);
    }
}
