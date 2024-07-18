package io.jeeyeon.app.ticketReserve.domain.concert;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConcertScheduleRepository {
    List<ConcertSchedule> findByConcertId(Long concertId);

    ConcertSchedule findByConcertIdAndConcertDate(Long concertId, LocalDateTime date);
}
