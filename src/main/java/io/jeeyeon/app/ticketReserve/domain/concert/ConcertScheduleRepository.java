package io.jeeyeon.app.ticketReserve.domain.concert;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConcertScheduleRepository {
    List<ConcertSchedule> findByConcertId(Long concertId);

    Optional<ConcertSchedule> findByConcertIdAndConcertDate(Long concertId, LocalDateTime date);
}
